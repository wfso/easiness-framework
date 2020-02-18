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
 * create by WFSO (仵士杰) at 2020/2/1 20:29
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel("支付宝手机APP支付异步通知对象")
public class AlipayAppAsyncNotification extends AlipayAsyncNotification {

  @ApiModelProperty("接口版本")
  private String version;

  @ApiModelProperty("优惠券信息")
  private String voucherDetailList;

  @ApiModelProperty("买家支付宝账号")
  private String buyerLogonId;

  @ApiModelProperty("卖家支付宝账号")
  private String sellerEmail;

  public AlipayAppAsyncNotification(Map<String, String> params) {
    super(params);

    // 必需参数
    setCharset(params.get("charset"));
    setPassbackParams(params.get("passback_params"));

    // 扩展参数
    setVersion(params.get("version"));
    setVoucherDetailList(params.get("voucher_detail_list"));
    setBuyerLogonId(params.get("buyer_logon_id"));
    setSellerEmail(params.get("seller_email"));
  }
}
