/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.wechat.properties;

import cn.ibestcode.easiness.pay.EasinessPayConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/28 18:52
 */
@Getter
@Setter
public class WechatProperties {
  @ApiModelProperty("APPId")
  private String appId;

  @ApiModelProperty("APPKey")
  private String appKey;

  @ApiModelProperty("商户号")
  private String mchId;

  @ApiModelProperty("下单地址")
  private String placeOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

  @ApiModelProperty("异常通知地址前缀")
  private String notifyUrlPrefix = EasinessPayConstant.ASYNC_NOTIFY_URL_PREFIX;


}
