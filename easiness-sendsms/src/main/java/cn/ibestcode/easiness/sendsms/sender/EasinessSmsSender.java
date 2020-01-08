/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.sender;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.sendsms.EasinessSendSmsConstant;
import cn.ibestcode.easiness.sendsms.exception.SendSmsException;
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
  protected SmsProperties smsProperties;

  @Autowired(required = false)
  protected EasinessConfiguration configuration;

  @Autowired(required = false)
  protected RedissonClient redissonClient;

  @Autowired
  protected List<SmsProvider> providers;

  protected Map<String, SmsProvider> providerMap;

  protected String type;

  @PostConstruct
  public void postConstruct() {
    this.type = smsProperties.getType();
    if (redissonClient != null) {
      RTopic rTopic = redissonClient.getTopic(EasinessSendSmsConstant.SUBSCRIBE_CONFIG_FIELD);
      rTopic.addListener(Integer.class, (s, m) -> clear());
    }
  }

  public SmsSenderResult sendSms(String phone, String template, Map<String, String> vars, String type) {
    boolean flag = true;
    if (configuration != null) {
      flag = configuration.getBooleanConfigure(EasinessSendSmsConstant.ENABLE_CONFIG_FIELD, true);
    }
    if (flag) {
      SmsProvider provider = getProviderByType(type);
      if (provider == null) {
        throw new SendSmsException("SmsProviderCanNotBeNull");
      }
      return provider.getSender().sendSms(phone, template, vars);
    }
    return null;
  }

  public SmsSenderResult sendSms(String phone, String template, Map<String, String> vars) {
    return sendSms(phone, template, vars, type);
  }

  protected void clear() {
    type = null;
    if (configuration != null) {
      type = configuration.getConfig(EasinessSendSmsConstant.DEFAULT_TYPE_CONFIG_FIELD);
    }
    if (StringUtils.isEmpty(type)) {
      type = smsProperties.getType();
    }
    for (SmsProvider provider : providers) {
      provider.clear();
    }
  }

  protected SmsProvider getProviderByType(String type) {
    if (providerMap == null) {
      synchronized (this) {
        if (providerMap == null) {
          for (SmsProvider provider : providers) {
            providerMap.put(provider.getType(), provider);
          }
        }
      }
    }
    if (!providerMap.containsKey(type)) {
      log.warn("No available provider was found by type [{}]", type);
      throw new SendSmsException("NotProviderExistOfType", type);
    }
    return providerMap.get(type);
  }

}
