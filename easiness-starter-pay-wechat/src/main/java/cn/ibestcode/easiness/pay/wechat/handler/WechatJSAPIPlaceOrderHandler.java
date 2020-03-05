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
import cn.ibestcode.easiness.pay.wechat.properties.WechatJSAPIProperties;
import cn.ibestcode.easiness.pay.wechat.utils.SignUtil;
import cn.ibestcode.easiness.utils.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/28 18:52
 */
@Component
@Slf4j
public class WechatJSAPIPlaceOrderHandler extends WechatPlaceOrderHandler {

  @Autowired
  private WechatJSAPIProperties properties;

  @Override
  public String supportType() {
    return EasinessPayWechatConstant.EASINESS_PAY_TYPE_JSAPI;
  }

  @Override
  protected PlaceOrderParams genPlaceOrderParams(EasinessOrder order, EasinessPay pay, EasinessPayPassbackParams passbackParams, Map<String, String> params) {
    PlaceOrderParams orderParams = new PlaceOrderParams();
    return orderParams;
  }

  protected void setResponseBody(WechatPlaceOrderResult result) throws JsonProcessingException {
    Map<String, String> appPayParam = new TreeMap<>();
    appPayParam.put("appId", result.getAppid());
    appPayParam.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
    appPayParam.put("nonceStr", RandomUtil.generateUnseparatedUuid());
    appPayParam.put("package", "prepay_id=" + result.getPrepay_id());
    appPayParam.put("signType", properties.getSignType());
    appPayParam.put("paySign", SignUtil.sign(appPayParam, properties.getAppKey(), properties.getSignType()));
    result.setResponseBody(objectMapper.writeValueAsString(appPayParam));
  }

  protected WechatJSAPIProperties getProperties() {
    return properties;
  }
}
