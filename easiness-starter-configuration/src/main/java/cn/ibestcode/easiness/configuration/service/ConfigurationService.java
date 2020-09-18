/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.configuration.service;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.configuration.model.Configuration;
import cn.ibestcode.easiness.configuration.repository.ConfigurationRepository;
import cn.ibestcode.easiness.core.base.service.BaseJpaService;
import org.redisson.api.RMapCache;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Service
public class ConfigurationService extends BaseJpaService<Configuration> implements EasinessConfiguration {
  @Autowired
  private ConfigurationRepository configurationRepository;

  @Autowired(required = false)
  private RedissonClient redissonClient;

  @Value("${easiness.configIdentification:EASINESS-CONFIGURATION}")
  private String configIdentification;

  @Override
  protected ConfigurationRepository getRepository() {
    return configurationRepository;
  }


  @PostConstruct
  private void postConstruct() {
    if (redissonClient != null) {
      RMapCache<String, String> mapCache = redissonClient.getMapCache(configIdentification);
      mapCache.clear();
      List<Configuration> allConfigs = configurationRepository.findAll();
      for (Configuration configuration : allConfigs) {
        mapCache.fastPut(configuration.getConfigKey(), configuration.getValue());
      }
    }
  }


  public void initConfigs() {
    postConstruct();
  }


  public List<Configuration> getAllByCategory(String category) {
    return configurationRepository.findByConfigKeyStartingWith(category + ".");
  }

  public String getConfig(String key) {
    if (redissonClient != null) {
      RMapCache<String, String> mapCache = redissonClient.getMapCache(configIdentification);
      return mapCache.get(key);
    }
    Configuration configuration = configurationRepository.findByConfigKey(key);
    if (configuration != null) {
      return configuration.getValue();
    }
    return null;
  }

  @Override
  @Transactional
  public List<Configuration> update(Iterable<Configuration> entities) {
    for (Configuration t : entities) {
      setConfigCache(t.getConfigKey(), t.getValue());
    }
    return super.update(entities);
  }


  @Transactional
  public void setConfig(String key, String value) {
    Configuration configuration = configurationRepository.findByConfigKey(key);
    if (configuration != null && configuration.isAvailable()) {
      configuration.setValue(value);
      update(configuration);
    } else {
      configuration = new Configuration();
      configuration.setConfigKey(key);
      configuration.setValue(value);
      create(configuration);
    }
  }

  public BigDecimal getDecimalConfig(String key) {
    String v = this.getConfig(key);
    if (v == null || v.trim().length() == 0) {
      return new BigDecimal(0);
    }
    return new BigDecimal(v);
  }

  public long getLongConfig(String key, long defaultValue) {
    String v = this.getConfig(key);
    if (v == null || v.trim().length() == 0) {
      return defaultValue;
    }
    return Long.parseLong(v);
  }

  public int getIntConfig(String key, int defaultValue) {
    String v = this.getConfig(key);
    if (v == null || v.trim().length() == 0) {
      return defaultValue;
    }
    return Integer.parseInt(v);
  }

  public boolean getBooleanConfigure(String key, boolean defaultValue) {
    String v = this.getConfig(key);
    if (v == null || v.trim().length() == 0) {
      return defaultValue;
    }
    return Boolean.parseBoolean(v);
  }

  @Transactional
  public void remove(String key) {
    Configuration configuration = configurationRepository.findByConfigKey(key);
    if (configuration != null) {
      configurationRepository.delete(configuration);
    }
    removeConfigCache(key);
  }

  @Override
  @Transactional
  public Configuration create(Configuration configuration) {
    super.create(configuration);
    setConfigCache(configuration.getConfigKey(), configuration.getValue());
    return configuration;
  }

  @Override
  @Transactional
  public Configuration update(Configuration configuration) {
    super.update(configuration);
    setConfigCache(configuration.getConfigKey(), configuration.getValue());
    return configuration;
  }


  @Override
  @Transactional
  public Configuration removeById(Long id) {
    Configuration configuration = configurationRepository.getOne(id);
    removeConfigCache(configuration.getConfigKey());
    return super.removeById(id);
  }

  @Override
  @Transactional
  public Configuration remove(Configuration configuration) {
    removeConfigCache(configuration.getConfigKey());
    return super.remove(configuration);
  }


  private void setConfigCache(String key, String value) {
    if (redissonClient != null) {
      RMapCache<String, String> mapCache = redissonClient.getMapCache(configIdentification);
      mapCache.fastPut(key, value);
      if (key.endsWith(".subscribe")) {
        RTopic rTopic = redissonClient.getTopic(key);
        rTopic.publish(1);
      }
    }
  }

  private void removeConfigCache(String key) {
    if (redissonClient != null) {
      RMapCache<String, String> mapCache = redissonClient.getMapCache(configIdentification);
      mapCache.remove(key);
    }
  }
}
