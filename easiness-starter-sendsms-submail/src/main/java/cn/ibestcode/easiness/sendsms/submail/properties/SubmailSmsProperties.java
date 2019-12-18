/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.submail.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Getter
@Setter
@Component
@ConfigurationProperties("easiness.sms.submail")
public class SubmailSmsProperties {

  private String appId;

  private String appKey;

  private String hookKey;

  private String smsUrl = "https://api.mysubmail.com/message/xsend.json";

  private String timestampUrl = "https://api.mysubmail.com/service/timestamp.json";
}
