/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.session.utils;

import org.apache.shiro.session.Session;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
public abstract class AbstractEasinessSession implements EasinessSession {

  protected abstract Session getSessionObject();

  @Override
  public void setSession(String key, Object value) {
    getSessionObject().setAttribute(key, value);
  }

  @Override
  public Object getSession(String key) {
    return getSessionObject().getAttribute(key);
  }

  @Override
  public Object removeSession(String key) {
    return getSessionObject().removeAttribute(key);
  }

  @Override
  public String getSessionId() {
    return getSessionObject().getId().toString();
  }
}
