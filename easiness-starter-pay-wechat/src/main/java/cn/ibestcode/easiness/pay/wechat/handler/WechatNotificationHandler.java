/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.wechat.handler;

import cn.ibestcode.easiness.pay.biz.EasinessPayBiz;
import cn.ibestcode.easiness.pay.exception.EasinessPayException;
import cn.ibestcode.easiness.pay.handler.EasinessPayNotifyHandler;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.service.EasinessPayService;
import cn.ibestcode.easiness.pay.wechat.domain.WechatAsyncNotification;
import cn.ibestcode.easiness.pay.wechat.properties.WechatProperties;
import cn.ibestcode.easiness.pay.wechat.utils.SignUtil;
import cn.ibestcode.easiness.spring.utils.CurrentRequestUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.TreeMap;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/3/4 18:52
 */
@Slf4j
public abstract class WechatNotificationHandler implements EasinessPayNotifyHandler {
  private static final XmlMapper xmlMapper = new XmlMapper();

  @Autowired
  protected EasinessPayService payService;
  @Autowired
  protected EasinessPayBiz payBiz;

  @Override
  public String notifyHandle(String payUuid) {
    String requestBody = CurrentRequestUtil.getBody();
    WechatProperties properties = getProperties();
    try {
      WechatAsyncNotification notification = xmlMapper.readValue(requestBody, WechatAsyncNotification.class);
      TreeMap<String, String> map = xmlMapper.readValue(requestBody, TreeMap.class);
      String signType = notification.getSign_type();
      if (StringUtils.isBlank(signType)) {
        signType = "HMAC-SHA256";
      }
      String sign = SignUtil.sign(map, properties.getAppKey(), signType);
      if (!sign.equalsIgnoreCase(notification.getSign())) {
        log.warn(sign);
        log.warn(notification.getSign());
        log.warn(requestBody);
        throw new EasinessPayException("WeChatPaySignFailed");
      }

      if (!notification.isSucceed()) {
        log.warn(requestBody);
        throw new EasinessPayException("WeChatPayNotSucceed");
      }

      EasinessPay pay = payService.getByUuid(payUuid);
      if (!String.valueOf(pay.getPrice()).equalsIgnoreCase(notification.getTotalAmount())) {
        log.warn(requestBody);
        throw new EasinessPayException("WeChatPayPriceError");
      }
      pay.setOnlineResultData(requestBody);
      pay.setOnlineUuid(notification.getTradeId());
      payService.update(pay);
      payBiz.setPayStatusPaid(payUuid);
      return "<xml>" +
        "<return_code><![CDATA[SUCCESS]]></return_code>" +
        "<return_msg><![CDATA[OK]]></return_msg>" +
        "</xml>";
    } catch (IOException e) {
      e.printStackTrace();
      log.warn(requestBody);
      log.warn(e.getMessage(), e);
    }
    log.warn(payUuid);
    return "";
  }

  protected abstract WechatProperties getProperties();

}
