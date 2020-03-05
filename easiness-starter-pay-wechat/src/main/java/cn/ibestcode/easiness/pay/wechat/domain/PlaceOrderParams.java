/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.pay.wechat.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/3/4 21:31
 */
@Setter
@Getter
@ToString
@ApiModel("下单参数-基类")
public class PlaceOrderParams {
  @ApiModelProperty("应用APPID")
  private String appid;
  @ApiModelProperty("商户号")
  private String mchId;
  @ApiModelProperty("随机字符串")
  private String nonceStr;
  @ApiModelProperty("签名")
  private String sign;
  @ApiModelProperty("签名类型")
  private String signType;
  @ApiModelProperty("商品描述")
  private String body;
  @ApiModelProperty("商户订单号")
  private String outTradeNo;
  @ApiModelProperty("标价金额")
  private String totalFee;
  @ApiModelProperty("终端IP")
  private String spbillCreateIp;
  @ApiModelProperty("通知地址")
  private String notifyUrl;
  @ApiModelProperty("交易类型")
  private String tradeType;
  @ApiModelProperty("附加数据")
  private String attach;
  @ApiModelProperty("标价币种")
  private String feeType;

}
