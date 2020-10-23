/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.session.cache;

import cn.ibestcode.easiness.utils.SerializationUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Deprecated
public class SerializedRedissonSessionCache implements Cache<Serializable, Session> {

  private final RMapCache<String, String> map;

  public SerializedRedissonSessionCache(RMapCache<String, String> map) {
    this.map = map;
  }

  public RMap<String, String> getMap() {
    return map;
  }


  @Override
  public Session get(Serializable key) throws CacheException {
    return (Session) SerializationUtil.deserialization(map.get(SerializationUtil.serialization(key)));
  }

  @Override
  public Session put(Serializable key, Session session) throws CacheException {
    // ttl 只所以要设置为 Session 过期时间的 5 倍，
    // 是为了保证 在启用 SessionValidationScheduler 时，
    // Session 可以被 SessionValidationScheduler 清理掉；
    // 并且在没有启用 SessionValidationScheduler 时，
    // 也可以自动过期由 redis 清理掉过期 Session ；
    map.put(SerializationUtil.serialization(key), SerializationUtil.serialization(session), session.getTimeout() * 5, TimeUnit.MILLISECONDS);
    return session;
  }

  @Override
  public Session remove(Serializable key) throws CacheException {
    return (Session) SerializationUtil.deserialization(map.remove(SerializationUtil.serialization(key)));
  }

  @Override
  public void clear() throws CacheException {
    map.clear();
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public Set<Serializable> keys() {
    Set<String> stringSet = map.keySet();
    Set<Serializable> kSet = new HashSet<>();
    stringSet.stream().forEach(string -> {
      kSet.add((Serializable) SerializationUtil.deserialization(string));
    });
    return kSet;
  }

  @Override
  public Collection<Session> values() {
    Collection<String> strings = map.values();
    Collection<Session> vs = new ArrayList<>();
    strings.stream().forEach(string -> {
      vs.add((Session) SerializationUtil.deserialization(string));
    });
    return vs;
  }
}
