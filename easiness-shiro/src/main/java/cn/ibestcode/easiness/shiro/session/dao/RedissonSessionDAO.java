/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.session.dao;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.redisson.api.RMapCache;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
public class RedissonSessionDAO extends EnterpriseCacheSessionDAO {


  private final RMapCache<Serializable, Session> rMap;

  public RedissonSessionDAO(RMapCache<Serializable, Session> rMap) {
    super();
    this.rMap = rMap;
  }

  @Override
  protected Session doReadSession(Serializable sessionId) {
    return rMap.get(sessionId);
  }

  @Override
  protected void doUpdate(Session session) {
    // ttl 只所以要设置为 Session 过期时间的 5 倍，
    // 是为了保证 在启用 SessionValidationScheduler 时，
    // Session 可以被 SessionValidationScheduler 清理掉；
    // 并且在没有启用 SessionValidationScheduler 时，
    // 也可以自动过期由 redis 清理掉过期 Session ；
    rMap.put(session.getId(), session, session.getTimeout() * 5, TimeUnit.MILLISECONDS);
  }

  @Override
  protected void doDelete(Session session) {
    rMap.remove(session.getId());
  }

}
