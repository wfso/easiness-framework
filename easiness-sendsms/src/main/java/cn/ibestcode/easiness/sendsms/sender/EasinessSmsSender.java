/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.sender;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.sendsms.properties.SmsProperties;
import cn.ibestcode.easiness.sendsms.provider.SmsProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/20 19:13
 */
@Slf4j
public class EasinessSmsSender implements SmsSender {

  @Autowired
  private SmsProperties smsProperties;

  @Autowired(required = false)
  private EasinessConfiguration configuration;

  @Autowired(required = false)
  private RedissonClient redissonClient;

  @Autowired
  private List<SmsProvider> providers;

  private String type;

  @PostConstruct
  public void postConstruct() {
    this.type = smsProperties.getType();
    if (redissonClient != null) {
      RTopic rTopic = redissonClient.getTopic("easiness.sms.subscribe");
      rTopic.addListener(Integer.class, (s, m) -> clear());
    }
  }

  public SmsSenderResult sendSms(String phone, String template, Map<String, String> vars, String type) {
    boolean flag = true;
    if (configuration != null) {
      flag = configuration.getBooleanConfigure("easiness.sms.enable", true);
    }
    if (flag) {
      for (SmsProvider provider : providers) {
        if (provider.supports(type)) {
          return provider.getSender().sendSms(phone, template, vars);
        }
      }
      log.warn("No available provider was found by type [{}]", type);
      return null;
    }
    return null;
  }

  public SmsSenderResult sendSms(String phone, String template, Map<String, String> vars) {
    return sendSms(phone, template, vars, type);
  }

  private void clear() {
    type = null;
    if (configuration != null) {
      type = configuration.getConfig("easiness.sms.type");
    }
    if (StringUtils.isEmpty(type)) {
      type = smsProperties.getType();
    }
    for (SmsProvider provider : providers) {
      provider.clear();
    }
  }

}