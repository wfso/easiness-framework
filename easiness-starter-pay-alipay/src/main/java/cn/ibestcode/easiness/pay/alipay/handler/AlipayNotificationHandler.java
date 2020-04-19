/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.handler;

import cn.ibestcode.easiness.pay.alipay.domain.AlipayAsyncNotification;
import cn.ibestcode.easiness.pay.alipay.properties.AlipayProperties;
import cn.ibestcode.easiness.pay.biz.EasinessPayBiz;
import cn.ibestcode.easiness.pay.exception.EasinessPayException;
import cn.ibestcode.easiness.pay.handler.EasinessPayNotifyHandler;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.service.EasinessPayService;
import cn.ibestcode.easiness.pay.utils.PriceUtils;
import cn.ibestcode.easiness.spring.utils.CurrentRequestUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/1 20:29
 */
@Slf4j
public abstract class AlipayNotificationHandler implements EasinessPayNotifyHandler {

  @Autowired
  protected EasinessPayService payService;

  @Autowired
  protected EasinessPayBiz payBiz;

  @Autowired
  protected ObjectMapper objectMapper;

  @Override
  public String notifyHandle(String payUuid) {
    EasinessPay easinessPay = payService.getByUuid(payUuid);
    Map<String, String> params = CurrentRequestUtil.getParameters();
    AlipayProperties properties = getAlipayProperties();
    AlipayAsyncNotification notification = genAlipayAsyncNotification(params);
    if (!signCheck(params, notification, properties)) {
      throw new EasinessPayException("AlipaySignError");
    }
    if (!notification.isSucceed() && !notification.isClosed()) {
      throw new EasinessPayException("AlipayUnknownStatus");
    }
    if (!checkPrice(easinessPay, notification)) {
      throw new EasinessPayException("AlipayPriceError");
    }
    try {
      easinessPay.setOnlineResultData(objectMapper.writeValueAsString(params));
      easinessPay.setOnlineUuid(notification.getTradeNo());
      payService.update(easinessPay);
      if (notification.isSucceed()) {
        payBiz.setPayStatusPaid(payUuid);
      } else if (notification.isClosed()) {
        payBiz.setPayStatusCancel(payUuid);
      }
      return "success";
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "fail";
  }

  protected abstract AlipayProperties getAlipayProperties();

  protected boolean checkPrice(EasinessPay pay, AlipayAsyncNotification notification) {
    String price = PriceUtils.transformPrice(pay.getPrice());
    return price.equalsIgnoreCase(notification.getTotalAmount());
  }

  protected AlipayAsyncNotification genAlipayAsyncNotification(Map<String, String> params) {
    return new AlipayAsyncNotification(params);
  }

  protected boolean signCheck(Map<String, String> params, AlipayAsyncNotification notification, AlipayProperties properties) {
    try {
      return AlipaySignature.rsaCheckV1(params, properties.getPublicKey(), notification.getCharset(), notification.getSignType());
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    return false;
  }

}
