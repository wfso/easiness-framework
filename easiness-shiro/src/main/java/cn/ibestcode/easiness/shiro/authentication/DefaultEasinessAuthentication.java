/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.authentication;

import cn.ibestcode.easiness.shiro.token.EasinessAuthenticationToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */

public class DefaultEasinessAuthentication implements EasinessAuthentication {

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof EasinessAuthenticationToken;
  }

  @Override
  public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken, String realmName) {
    EasinessAuthenticationToken token = (EasinessAuthenticationToken) authenticationToken;
    return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), realmName);
  }

}
