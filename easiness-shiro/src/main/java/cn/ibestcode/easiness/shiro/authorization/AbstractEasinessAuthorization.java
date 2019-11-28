/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.authorization;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import java.util.Collection;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
public abstract class AbstractEasinessAuthorization implements EasinessAuthorization {

  @Override
  public boolean supports(Object object) {
    return object instanceof Long;
  }

  @Override
  public AuthorizationInfo getAuthorizationInfo(Object object) {
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    long userId = (long) object;
    info.addRoles(getAvailableRoleCodesByUserId(userId));
    info.addStringPermissions(getAvailablePermissionCodesByUserId(userId));
    return info;
  }

  protected abstract Collection<String> getAvailablePermissionCodesByUserId(long userId);

  protected abstract Collection<String> getAvailableRoleCodesByUserId(long userId);

}
