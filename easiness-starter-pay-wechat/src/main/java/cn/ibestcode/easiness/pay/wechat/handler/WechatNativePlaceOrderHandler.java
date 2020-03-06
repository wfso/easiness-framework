/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.wechat.handler;

import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.wechat.EasinessPayWechatConstant;
import cn.ibestcode.easiness.pay.wechat.domain.PlaceOrderParams;
import cn.ibestcode.easiness.pay.wechat.domain.WechatPlaceOrderResult;
import cn.ibestcode.easiness.pay.wechat.properties.WechatNativeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/28 18:52
 */
@Component
@Slf4j
public class WechatNativePlaceOrderHandler extends WechatPlaceOrderHandler {

  @Autowired
  private WechatNativeProperties properties;

  @Override
  public String supportType() {
    return EasinessPayWechatConstant.EASINESS_PAY_TYPE_NATIVE;
  }

  @Override
  protected PlaceOrderParams genPlaceOrderParams(EasinessOrder order, EasinessPay pay, EasinessPayPassbackParams passbackParams, Map<String, String> params) {
    PlaceOrderParams orderParams = new PlaceOrderParams();
    return orderParams;
  }

  protected void setResponseBody(WechatPlaceOrderResult result) throws IOException {
    result.setResponseBody(result.getCode_url());
  }

  protected WechatNativeProperties getProperties() {
    return properties;
  }
}
