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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/12 20:29
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "easiness.pay.alipay.pc-web")
public class AlipayPCWebProperties {
  private String appId;
  private String privateKey;
  private String publicKey;
  private String format = "json";
  private String charset = "UTF-8";
  private String serverUrl = "https://openapi.alipay.com/gateway.do";
  private String signType = "RSA2";
  private String notifyUrlPrefix = EasinessPayConstant.ASYNC_NOTIFY_URL_PREFIX;
  private String returnUrl = "";
  private String productCode = "FAST_INSTANT_TRADE_PAY";

}
