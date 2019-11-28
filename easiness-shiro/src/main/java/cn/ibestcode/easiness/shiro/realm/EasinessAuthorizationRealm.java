/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.realm;

import cn.ibestcode.easiness.shiro.authentication.EasinessAuthentication;
import cn.ibestcode.easiness.shiro.authorization.EasinessAuthorization;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Slf4j
public class EasinessAuthorizationRealm extends AuthorizingRealm {

  @Autowired
  protected List<EasinessAuthentication> authentications;
  @Autowired
  protected List<EasinessAuthorization> authorizations;

  public EasinessAuthorizationRealm() {
    this.authentications = new ArrayList<>();
    this.authorizations = new ArrayList<>();
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    for (EasinessAuthentication authentication : authentications) {
      if (authentication.supports(token)) {
        return true;
      }
    }
    return false;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
    for (EasinessAuthentication authentication : authentications) {
      if (authentication.supports(authenticationToken)) {
        return authentication.getAuthenticationInfo(authenticationToken, getName());
      }
    }
    log.warn("No supported EasinessAuthentication found");
    return null;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    Object principal = principals.getPrimaryPrincipal();
    for (EasinessAuthorization authorization : authorizations) {
      if (authorization.supports(principal)) {
        return authorization.getAuthorizationInfo(principal);
      }
    }
    log.warn("No supported EasinessAuthorization found");
    return null;
  }

}
