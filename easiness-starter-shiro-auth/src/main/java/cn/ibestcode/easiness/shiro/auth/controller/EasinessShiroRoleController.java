/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.auth.controller;

import cn.ibestcode.easiness.auth.constant.EasinessRoleConstant;
import cn.ibestcode.easiness.auth.controller.EasinessRoleController;
import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import cn.ibestcode.easiness.auth.query.EasinessRoleQueryVo;
import cn.ibestcode.easiness.core.paging.DefaultPageableGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/28 19:19
 */
@RestController
@RequestMapping("/api/easiness/role")
@Api(tags = "角色管理")
public class EasinessShiroRoleController extends EasinessRoleController {

  // region 角色管理
  @Override
  @PostMapping
  @ApiOperation("添加角色")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void add(@RequestBody EasinessRole role) {
    super.add(role);
  }

  @Override
  @PutMapping
  @ApiOperation("修改角色")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void edit(@RequestBody EasinessRole role) {
    super.edit(role);
  }

  @Override
  @DeleteMapping("{code}")
  @ApiOperation("删除角色")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void remove(@PathVariable String code) {
    super.remove(code);
  }

  @Override
  @GetMapping("all")
  @ApiOperation("获取所有角色-不分页")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public List<EasinessRole> getAll() {
    return super.getAll();
  }

  @Override
  @GetMapping
  @ApiOperation("根据条件搜索角色-带分页")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public Page<EasinessRole> getPage(EasinessRoleQueryVo filterGenerator, DefaultPageableGenerator pageableGenerator) {
    return super.getPage(filterGenerator, pageableGenerator);
  }

  // endregion


  // region 角色的权限管理
  @Override
  @GetMapping("{code}/permissions")
  @ApiOperation("获取角色的权限列表")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public List<EasinessPermission> getPermissions(@PathVariable String code) {
    return super.getPermissions(code);
  }

  @Override
  @PutMapping("{code}/permissions")
  @ApiOperation("设置“角色-权限”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void setPermissions(@PathVariable String code, @RequestBody List<String> permissionCodes) {
    super.setPermissions(code, permissionCodes);
  }

  @Override
  @PostMapping("{code}/permissions")
  @ApiOperation("添加“角色-权限”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void addPermissions(@PathVariable String code, @RequestBody List<String> permissionCodes) {
    super.addPermissions(code, permissionCodes);
  }

  @Override
  @DeleteMapping("{code}/permissions")
  @ApiOperation("删除“角色-权限”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void removePermissions(@PathVariable String code, @RequestBody List<String> permissionCodes) {
    super.removePermissions(code, permissionCodes);
  }

  // endregion

  // region 与角色关系的用户管理
  @Override
  @PutMapping("{code}/users")
  @ApiOperation("设置“用户-角色”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void setUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    super.setUsers(code, userIds);
  }

  @Override
  @PostMapping("{code}/users")
  @ApiOperation("添加“用户-角色”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void addUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    super.addUsers(code, userIds);
  }

  @Override
  @DeleteMapping("{code}/users")
  @ApiOperation("删除“用户-角色”")
  @RequiresRoles(EasinessRoleConstant.EASINESS_DEFAULT_SYSTEM_ROLE)
  public void removeUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    super.removeUsers(code, userIds);
  }

  // endregion
}
