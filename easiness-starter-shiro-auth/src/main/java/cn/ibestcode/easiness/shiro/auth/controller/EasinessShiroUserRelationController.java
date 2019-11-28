/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.auth.controller;

import cn.ibestcode.easiness.auth.constant.EasinessRoleConstant;
import cn.ibestcode.easiness.auth.controller.EasinessUserRelationController;
import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/28 19:25
 */
@RestController
@RequestMapping("/api/easiness/user")
@Api(tags = "用户管理")
public class EasinessShiroUserRelationController extends EasinessUserRelationController {
  // region 用户的角色管理
  @Override
  @GetMapping("{userId}/roles")
  @ApiOperation("获取用户的角色列表")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public List<EasinessRole> getRoles(@PathVariable long userId) {
    return super.getRoles(userId);
  }

  @Override
  @PutMapping("{userId}/roles")
  @ApiOperation("设置“用户-角色”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void setRoles(@PathVariable long userId, @RequestBody List<String> roleCodes) {
    super.setRoles(userId, roleCodes);
  }

  @Override
  @PostMapping("{userId}/roles")
  @ApiOperation("添加“用户-角色”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void addRoles(@PathVariable long userId, @RequestBody List<String> roleCodes) {
    super.addRoles(userId, roleCodes);
  }

  @Override
  @DeleteMapping("{userId}/roles")
  @ApiOperation("删除“用户-角色”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void removeRoles(@PathVariable long userId, @RequestBody List<String> roleCodes) {
    super.removeRoles(userId, roleCodes);
  }


  // endregion

  // region 用户的权限管理

  @Override
  @GetMapping("{userId}/permissions")
  @ApiOperation("获取用户的权限列表")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public List<EasinessPermission> getPermissions(@PathVariable long userId) {
    return super.getPermissions(userId);
  }

  @Override
  @PutMapping("{userId}/permissions")
  @ApiOperation("设置“用户-权限”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void setPermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    super.setPermissions(userId, permissionCodes);
  }

  @Override
  @PostMapping("{userId}/permissions")
  @ApiOperation("添加“用户-权限”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void addPermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    super.addPermissions(userId, permissionCodes);
  }

  @Override
  @DeleteMapping("{userId}/permissions")
  @ApiOperation("删除“用户-权限”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void removePermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    super.removePermissions(userId, permissionCodes);
  }

  // endregion

  // region 用户的排除权限管理

  @Override
  @GetMapping("{userId}/excludePermissions")
  @ApiOperation("获取用户的排除权限列表")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public List<EasinessPermission> getExcludePermissions(@PathVariable long userId) {
    return super.getExcludePermissions(userId);
  }

  @Override
  @PutMapping("{userId}/excludePermissions")
  @ApiOperation("设置“用户-排除-权限”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void setExcludePermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    super.setExcludePermissions(userId, permissionCodes);
  }

  @Override
  @PostMapping("{userId}/excludePermissions")
  @ApiOperation("添加“用户-排除-权限”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void addExcludePermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    super.addExcludePermissions(userId, permissionCodes);
  }

  @Override
  @DeleteMapping("{userId}/excludePermissions")
  @ApiOperation("删除“用户-排除-权限”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void removeExcludePermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    super.removeExcludePermissions(userId, permissionCodes);
  }

  // endregion

  // region 用户的所有权限
  // 包括从角色关系过来

  @Override
  @GetMapping("{userId}/availablePermissions")
  @ApiOperation("获取用户有效权限列表")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public List<EasinessPermission> getAvailablePermissions(@PathVariable long userId) {
    return super.getAvailablePermissions(userId);
  }

  // endregion
}
