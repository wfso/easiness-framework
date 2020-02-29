/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.domain;

import cn.ibestcode.easiness.pay.domain.PlaceOrderResult;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/12 20:29
 */
@Getter
@Setter
@ApiModel("支付宝下单接口通用返回值")
public class AlipayPlaceOrderResult implements PlaceOrderResult, Serializable {
  private String responseBody;
  private boolean succeed = true;

  @Override
  public String getTradeId() {
    return null;
  }

  @Override
  public String getOutTradeId() {
    return null;
  }

  @Override
  public String toJSON() {
    return null;
  }
}
