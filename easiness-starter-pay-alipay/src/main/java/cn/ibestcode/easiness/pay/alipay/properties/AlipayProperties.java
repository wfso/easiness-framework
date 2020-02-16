/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.properties;

import cn.ibestcode.easiness.pay.EasinessPayConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/1 20:29
 */
@Getter
@Setter
public class AlipayProperties {
  private String appId;
  private String privateKey;
  private String publicKey;
  private String format = "json";
  private String charset = "UTF-8";
  private String serverUrl = "https://openapi.alipay.com/gateway.do";
  private String signType = "RSA2";
  private String notifyUrlPrefix = EasinessPayConstant.ASYNC_NOTIFY_URL_PREFIX;
  private String returnUrl = "";
  private String productCode;

}
