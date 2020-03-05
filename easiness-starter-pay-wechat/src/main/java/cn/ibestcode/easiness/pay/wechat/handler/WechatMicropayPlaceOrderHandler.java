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
import cn.ibestcode.easiness.pay.exception.EasinessPayException;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.wechat.EasinessPayWechatConstant;
import cn.ibestcode.easiness.pay.wechat.domain.PlaceOrderParams;
import cn.ibestcode.easiness.pay.wechat.domain.WechatPlaceOrderResult;
import cn.ibestcode.easiness.pay.wechat.properties.WechatMicropayProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/28 18:52
 */
@Component
@Slf4j
public class WechatMicropayPlaceOrderHandler extends WechatPlaceOrderHandler {

  @Autowired
  private WechatMicropayProperties properties;

  @Override
  public String supportType() {
    return EasinessPayWechatConstant.EASINESS_PAY_TYPE_MICROPAY;
  }

  @Override
  protected MicropayPlaceOrderParams genPlaceOrderParams(EasinessOrder order, EasinessPay pay, EasinessPayPassbackParams passbackParams, Map<String, String> params) {
    MicropayPlaceOrderParams orderParams = new MicropayPlaceOrderParams();
    if (!params.containsKey("auth_code")) {
      easinessPayBiz.setPayStatusCancel(pay.getUuid());
      throw new EasinessPayException("RequireParam:auth_code");
    }
    orderParams.setAuthCode(params.get("auth_code"));
    return orderParams;
  }

  protected void setResponseBody(WechatPlaceOrderResult result) throws JsonProcessingException {
    result.setResponseBody("{}");
  }

  protected WechatMicropayProperties getProperties() {
    return properties;
  }

  @Setter
  @Getter
  @ToString
  @ApiModel("下单参数-Micropay")
  private static class MicropayPlaceOrderParams extends PlaceOrderParams {
    @ApiModelProperty("场景信息")
    private String authCode;
  }
}
