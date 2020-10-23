/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.spring.utils;

import javax.servlet.http.HttpSession;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/5 12:28
 */
public class CurrentHttpSessionUtil {

  public static void setAttribute(String key, Object value) {
    ServletUtil.getHttpSession().setAttribute(key, value);
  }

  public static Object getAttribute(String key) {
    return ServletUtil.getHttpSession().getAttribute(key);
  }

  public static Object removeAttribute(String key) {
    Object object = ServletUtil.getHttpSession().getAttribute(key);
    ServletUtil.getHttpSession().removeAttribute(key);
    return object;
  }

  public static String getSessionId() {
    // 等价 return getSession().getId();
    return ServletUtil.getServletRequestAttributes().getSessionId();
  }

  private static HttpSession getSession() {
    return ServletUtil.getHttpSession();
  }
}
