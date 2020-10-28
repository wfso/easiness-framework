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
public class EasinessLoginHandler {

  @Autowired
  private List<EasinessLoginProvider> providers;

  private Map<String, EasinessLoginProvider> providerMap;

  public long handle(String type) {
    EasinessLoginProvider provider = getProviderByType(type);
    if (provider == null) {
      throw new EasinessAuthenticationException("LoginProviderCanNotBeNull");
    }
    return provider.login();
  }

  private EasinessLoginProvider getProviderByType(String type) {
    if (providerMap == null) {
      synchronized (this) {
        if (providerMap == null) {
          providerMap = new HashMap<>();
          for (EasinessLoginProvider provider : providers) {
            providerMap.put(provider.supportType(), provider);
          }
        }
      }
    }
    if (!providerMap.containsKey(type)) {
      throw new EasinessAuthenticationException("NotLoginProviderExistOfType", type);
    }
    return providerMap.get(type);
  }

}
