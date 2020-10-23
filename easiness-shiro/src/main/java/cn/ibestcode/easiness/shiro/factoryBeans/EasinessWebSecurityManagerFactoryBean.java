/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.factoryBeans;

import cn.ibestcode.easiness.shiro.session.cache.RedissonSessionCache;
import cn.ibestcode.easiness.shiro.session.dao.RedissonSessionDAO;
import cn.ibestcode.easiness.shiro.session.manager.EasinessSessionManager;
import cn.ibestcode.easiness.shiro.session.properties.EasinessSessionProperties;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Deprecated
public class EasinessWebSecurityManagerFactoryBean implements FactoryBean<SecurityManager> {

  @Autowired
  private Realm realm;

  @Autowired(required = false)
  private RedissonClient redissonClient;

  @Autowired
  private EasinessSessionProperties sessionProperties;

  @Override
  public SecurityManager getObject() {

    Assert.notNull(realm, "No Realm was found available");

    // 实例化 EasinessSessionManager
    EasinessSessionManager sessionManager = new EasinessSessionManager();
    sessionManager.setTokenIdentification(sessionProperties.getTokenIdentification());
    sessionManager.setGlobalSessionTimeout(sessionProperties.getTimeout());
    sessionManager.setDeleteInvalidSessions(true);
    sessionManager.setSessionValidationSchedulerEnabled(true);
    sessionManager.setSessionValidationInterval(sessionProperties.getSessionValidationInterval());

    // 如果 RedissonClient 实例存在，测使用 redis 进行 Session 存储 以实现分布式服务的Session 同步
    if (redissonClient != null) {
      RMapCache rMap = redissonClient.getMapCache(sessionProperties.getRedissonStoreName());
      RMapCache rMapCache = redissonClient.getMapCache(sessionProperties.getRedissonStoreName() + "-cache");
      EnterpriseCacheSessionDAO sessionDAO = new RedissonSessionDAO(rMap);
      sessionManager.setSessionDAO(sessionDAO);
      sessionManager.setCacheManager(new AbstractCacheManager() {
        @Override
        protected Cache createCache(String s) throws CacheException {
          return new RedissonSessionCache(rMapCache);
        }
      });
    }


    // 实例化 DefaultWebSecurityManager
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

    securityManager.setRealm(realm);


    securityManager.setSessionManager(sessionManager);
    return securityManager;
  }

  @Override
  public Class<?> getObjectType() {
    return SecurityManager.class;
  }
}
