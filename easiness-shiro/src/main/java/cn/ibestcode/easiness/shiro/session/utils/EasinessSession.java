/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.session.utils;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
public interface EasinessSession {

  void setAttribute(String key, Object value);

  Object getAttribute(String key);

  Object removeAttribute(String key);

  String getSessionId();
}
