/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.auth.biz;

import cn.ibestcode.easiness.auth.constant.EasinessAuthConstant;
import cn.ibestcode.easiness.auth.domain.EasinessLoginBeforeResult;
import cn.ibestcode.easiness.auth.domain.EasinessLoginResult;
import cn.ibestcode.easiness.auth.domain.RoleAndPermissionResult;
import cn.ibestcode.easiness.auth.exception.EasinessAuthenticationException;
import cn.ibestcode.easiness.auth.handler.EasinessLoginHandler;
import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import cn.ibestcode.easiness.auth.model.EasinessUser;
import cn.ibestcode.easiness.auth.service.EasinessRoleService;
import cn.ibestcode.easiness.auth.service.EasinessUserRelationService;
import cn.ibestcode.easiness.utils.MacUtil;
import cn.ibestcode.easiness.utils.RandomUtil;
import cn.ibestcode.easiness.utils.SpringBeanUtilsExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
public abstract class AbstractEasinessAuthBiz implements EasinessAuthBiz {

  @Autowired
  protected List<EasinessFetchUserBiz> fetchUserBizList;
  @Autowired
  protected EasinessUserRelationBiz userRelationBiz;
  @Autowired
  protected EasinessUserRelationService userRelationService;
  @Autowired
  protected EasinessRoleService roleService;
  @Autowired
  protected EasinessLoginHandler loginHandler;


  @Override
  public EasinessLoginBeforeResult loginBefore(String username) {
    EasinessUser user = getUserByIdentification(username);

    String token = RandomUtil.generateUnseparatedUuid();
    setSession(EasinessAuthConstant.TOKEN_FIELD_SESSION_NAME, token);
    setSession(EasinessAuthConstant.USER_FIELD_SESSION_NAME, username);
    EasinessLoginBeforeResult result = new EasinessLoginBeforeResult();
    result.setToken(token);

    if (user != null) {
      result.setSalt(user.getSalt());
    } else {
      result.setSalt(RandomUtil.generateUnseparatedUuid());
    }

    return result;
  }

  @Override
  public EasinessLoginResult login(String type) {
    long userId = loginHandler.handle(type);
    recordLoginStatus(userId);
    setSession(EasinessAuthConstant.USER_ID_SESSION_NAME, userId);
    RoleAndPermissionResult roleAndPermissionResult = checkRole(null);
    EasinessLoginResult result = new EasinessLoginResult();
    SpringBeanUtilsExt.copyPropertiesIgnoreEmpty(roleAndPermissionResult, result);
    result.setEasinessToken(getSessionId());
    return result;
  }

  @Override
  public EasinessLoginResult checkLogin() {
    EasinessLoginResult result = new EasinessLoginResult();
    result.setEasinessToken(getSessionId());
    result.setMasterRole(getMasterRole());
    result.setPermissions(getAvailablePermissions());
    result.setRoles(getLoginRoles());
    return result;
  }


  @Override
  public RoleAndPermissionResult checkRole(String code) {
    RoleAndPermissionResult result = new RoleAndPermissionResult();
    List<String> roleCodes = getLoginRoleCodes();

    List<String> excludeRoles = getExcludeRoleCodes();

    // 前端暂时不需要权限
    for (String roleCode : roleCodes) {
      if (!excludeRoles.contains(roleCode)) {
        result.getRoles().add(roleService.getByCode(roleCode));
        result.getPermissions().addAll(roleService.getPermissionsByRoleCode(roleCode));
      }
    }

    // 如果用户选择了主角色，则尝试设置主角色
    if (StringUtils.isNotBlank(code)) {
      if (roleCodes.contains(code) && !excludeRoles.contains(code)) {
        result.setMasterRole(roleService.getByCode(code));
      }
    }

    // 如果用户只有一个可用角色，则设置为主角色
    if (result.getRoles().size() == 1) {
      EasinessRole role = roleService.getByCode(roleCodes.get(0));
      result.setMasterRole(role);
    }

    // 如果有主角色，则触发同步登录角色的事件
    if (result.getMasterRole() != null) {
      setSession(EasinessAuthConstant.ROLE_ID_SESSION_NAME, result.getMasterRole().getId());
    }

    if (result.getRoles().size() == 0) {
      logout();
      throw new EasinessAuthenticationException("NoValidRole");
    }

    return result;
  }

  @Override
  public long getLoginUserId() {
    return (long) getSession(EasinessAuthConstant.USER_ID_SESSION_NAME);
  }

  @Override
  public long getMasterRoleId() {
    return (long) getSession(EasinessAuthConstant.ROLE_ID_SESSION_NAME);
  }

  @Override
  public EasinessRole getMasterRole() {
    return roleService.getById(getMasterRoleId());
  }


  @Override
  public List<EasinessRole> getLoginRoles() {
    return userRelationService.getRolesByUserId(getLoginUserId());
  }

  @Override
  public List<String> getLoginRoleCodes() {
    return userRelationService.getRoleCodesByUserId(getLoginUserId());
  }

  @Override
  public List<String> getAvailablePermissionCodes() {
    return userRelationBiz.getAvailablePermissionCodesByUserId(getLoginUserId());
  }

  @Override
  public List<EasinessPermission> getAvailablePermissions() {
    return userRelationBiz.getAvailablePermissionsByUserId(getLoginUserId());
  }

  @Override
  public List<String> getCurrentPermissionCodes() {
    List<String> permissionCodes = userRelationService.getPermissionCodesByUserId(getLoginUserId());
    EasinessRole masterRole = roleService.getById(getMasterRoleId());
    List<String> rolePermissionCodes = roleService.getPermissionCodesByRoleCode(masterRole.getCode());
    permissionCodes.addAll(rolePermissionCodes);
    List<String> excludePermissionCodes = userRelationService.getExcludePermissionCodesByUserId(getLoginUserId());
    permissionCodes.removeAll(excludePermissionCodes);
    return permissionCodes;
  }

  @Override
  public List<EasinessPermission> getCurrentPermissions() {
    List<EasinessPermission> permissions = userRelationService.getPermissionsByUserId(getLoginUserId());
    EasinessRole masterRole = roleService.getById(getMasterRoleId());
    List<EasinessPermission> rolePermissions = roleService.getPermissionsByRoleCode(masterRole.getCode());
    permissions.addAll(rolePermissions);
    List<String> excludePermissionCodes = userRelationService.getExcludePermissionCodesByUserId(getLoginUserId());
    List<EasinessPermission> result = new ArrayList<>();
    for (EasinessPermission permission : permissions) {
      if (!excludePermissionCodes.contains(permission.getCode())) {
        result.add(permission);
      }
    }
    return result;
  }


  public EasinessUser getUserByIdentification(String identification) {
    EasinessUser user;
    for (EasinessFetchUserBiz biz : fetchUserBizList) {
      user = biz.getByIdentification(identification);
      if (user != null) {
        return user;
      }
    }
    return null;
  }

  @Override
  public boolean validateCredentials(String dbPassword, String requestPassword) {
    String token = (String) removeSession(EasinessAuthConstant.TOKEN_FIELD_SESSION_NAME);
    String userName = (String) removeSession(EasinessAuthConstant.USER_FIELD_SESSION_NAME);
    if (token == null || userName == null) {
      return false;
    }
    String password = MacUtil.hmacSha256Hex(dbPassword + userName, token);
    return StringUtils.isNotEmpty(dbPassword)
      && StringUtils.isNotEmpty(requestPassword)
      && requestPassword.equalsIgnoreCase(password);
  }

  protected List<String> getExcludeRoleCodes() {
    return new ArrayList<>();
  }

  /**
   * 记录登录状态
   * 保存登录信息到 SESSION 中
   *
   * @param userId 用户ID
   */
  protected abstract void recordLoginStatus(long userId);

}
