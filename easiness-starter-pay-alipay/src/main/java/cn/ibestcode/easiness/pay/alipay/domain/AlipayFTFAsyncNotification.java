/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.pay.alipay.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/8 14:43
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel("支付宝当面付异步通知对象")
public class AlipayFTFAsyncNotification extends AlipayAsyncNotification {

  @ApiModelProperty("买家支付宝账号")
  private String buyerLogonId;

  @ApiModelProperty("卖家支付宝账号")
  private String sellerEmail;

  @ApiModelProperty("实际退款金额")
  private String sendBackFee;

  public AlipayFTFAsyncNotification(Map<String, String> params) {
    super(params);

    // 扩展参数
    setBuyerLogonId(params.get("buyer_logon_id"));
    setSellerEmail(params.get("seller_email"));
    setSendBackFee(params.get("send_back_fee"));
  }
}
