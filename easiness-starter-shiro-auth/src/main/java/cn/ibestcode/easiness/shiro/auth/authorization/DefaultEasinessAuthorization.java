/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.auth.authorization;

import cn.ibestcode.easiness.auth.biz.EasinessUserRelationBiz;
import cn.ibestcode.easiness.auth.service.EasinessUserRelationService;
import cn.ibestcode.easiness.shiro.authorization.AbstractEasinessAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Component
public class DefaultEasinessAuthorization extends AbstractEasinessAuthorization {

  @Autowired
  protected EasinessUserRelationService userService;

  @Autowired
  protected EasinessUserRelationBiz userBiz;

  protected Collection<String> getAvailablePermissionCodesByUserId(long userId) {
    return userBiz.getAvailablePermissionCodesByUserId(userId);
  }

  protected Collection<String> getAvailableRoleCodesByUserId(long userId) {
    return userService.getRoleCodesByUserId(userId);
  }

}
