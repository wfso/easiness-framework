/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.auth.handler;

import cn.ibestcode.easiness.auth.exception.EasinessAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/25 19:56
 */
@Component
public class EasinessLoginHandlerBus {

  @Autowired
  private List<EasinessLoginHandler> loginHandlerList;

  private Map<String, EasinessLoginHandler> loginHandlerMap;

  public long loginHandle(String type) {
    EasinessLoginHandler handler = getHandlerByType(type);
    if (handler == null) {
      throw new NullPointerException("LoginHandlerCanNotBeNull");
    }
    return handler.loginHandle();
  }

  private EasinessLoginHandler getHandlerByType(String type) {
    if (loginHandlerMap == null) {
      loginHandlerMap = new HashMap<>();
      for (EasinessLoginHandler handler : loginHandlerList) {
        loginHandlerMap.put(handler.supportType(), handler);
      }
    }
    if (!loginHandlerMap.containsKey(type)) {
      throw new EasinessAuthenticationException("NotLoginHandlerExistOfType", type);
    }
    return loginHandlerMap.get(type);
  }

}
