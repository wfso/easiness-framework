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

  protected abstract Session getSession();

  @Override
  public void setAttribute(String key, Object value) {
    getSession().setAttribute(key, value);
  }

  @Override
  public Object getAttribute(String key) {
    return getSession().getAttribute(key);
  }

  @Override
  public Object removeAttribute(String key) {
    return getSession().removeAttribute(key);
  }

  @Override
  public String getSessionId() {
    return getSession().getId().toString();
  }
}
