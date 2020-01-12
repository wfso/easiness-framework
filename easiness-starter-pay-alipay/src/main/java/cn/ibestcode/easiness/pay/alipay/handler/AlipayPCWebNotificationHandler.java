/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.handler;

import cn.ibestcode.easiness.pay.alipay.EasinessPayAlipayConstant;
import cn.ibestcode.easiness.pay.alipay.domain.AlipayPCWebAsyncNotification;
import cn.ibestcode.easiness.pay.alipay.properties.AlipayPCWebProperties;
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
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/12 20:29
 */
@Component
@Slf4j
public class AlipayPCWebNotificationHandler implements EasinessPayNotifyHandler {
  @Override
  public String supportType() {
    return EasinessPayAlipayConstant.EASINESS_PAY_TYPE_PC_WEB;
  }

  @Autowired
  private EasinessPayService payService;

  @Autowired
  private AlipayPCWebProperties properties;

  @Autowired
  private EasinessPayBiz payBiz;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String notifyHandle(String payUuid) {
    EasinessPay easinessPay = payService.getByUuid(payUuid);
    Map<String, String> params = CurrentRequestUtil.getParameters();
    AlipayPCWebAsyncNotification notification = new AlipayPCWebAsyncNotification(params);
    try {
      boolean signVerified = AlipaySignature.rsaCheckV1(params, properties.getPublicKey(), notification.getCharset(), notification.getSignType());
      if (!signVerified) {
        throw new EasinessPayException("AlipaySignError");
      }
      if (!notification.isSucceed()) {
        throw new EasinessPayException("AlipayNotSucceed");
      }
      if (!checkPrice(easinessPay, notification)) {
        throw new EasinessPayException("AlipayPriceError");
      }
      easinessPay.setOnlineResultData(objectMapper.writeValueAsString(params));
      easinessPay.setOnlineUuid(notification.getTradeNo());
      payService.update(easinessPay);
      payBiz.setPayStatusPaid(payUuid);
      return "success";
    } catch (AlipayApiException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "fail";
  }

  private boolean checkPrice(EasinessPay pay, AlipayPCWebAsyncNotification notification) {
    String price = PriceUtils.transformPrice(pay.getPrice());
    return price.equalsIgnoreCase(notification.getTotalAmount());
  }

}
