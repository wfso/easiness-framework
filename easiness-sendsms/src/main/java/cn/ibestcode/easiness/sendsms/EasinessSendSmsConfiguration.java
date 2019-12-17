/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms;

import cn.ibestcode.easiness.sendsms.hook.EasinessSmsHookBus;
import cn.ibestcode.easiness.sendsms.properties.SmsProperties;
import cn.ibestcode.easiness.sendsms.sender.EasinessSmsSender;
import cn.ibestcode.easiness.sendsms.sender.SmsSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/20 19:13
 */
@Configuration
@ComponentScan
public class EasinessSendSmsConfiguration {

  @Bean
  @ConditionalOnMissingBean(SmsProperties.class)
  public SmsProperties smsProperties() {
    return new SmsProperties();
  }

  @Bean
  @ConditionalOnMissingBean(SmsSender.class)
  public EasinessSmsSender smsSender() {
    return new EasinessSmsSender();
  }

  @Bean
  @ConditionalOnMissingBean(EasinessSmsHookBus.class)
  public EasinessSmsHookBus easinessSmsHookBus() {
    return new EasinessSmsHookBus();
  }
}
