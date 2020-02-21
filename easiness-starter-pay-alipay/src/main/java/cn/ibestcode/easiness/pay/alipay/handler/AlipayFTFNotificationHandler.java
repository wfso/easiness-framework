/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.handler;

import cn.ibestcode.easiness.pay.alipay.EasinessPayAlipayConstant;
import cn.ibestcode.easiness.pay.alipay.domain.AlipayFTFAsyncNotification;
import cn.ibestcode.easiness.pay.alipay.properties.AlipayFTFProperties;
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
public class AlipayFTFNotificationHandler extends AlipayNotificationHandler {
  @Override
  public String supportType() {
    return EasinessPayAlipayConstant.EASINESS_PAY_TYPE_FTF;
  }

  @Autowired
  private AlipayFTFProperties properties;

  @Override
  protected AlipayFTFProperties getAlipayProperties() {
    return properties;
  }

  protected AlipayFTFAsyncNotification genAlipayAsyncNotification(Map<String, String> params) {
    return new AlipayFTFAsyncNotification(params);
  }
}
