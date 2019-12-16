/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.auth.controller;

import cn.ibestcode.easiness.auth.constant.EasinessRoleConstant;
import cn.ibestcode.easiness.auth.controller.EasinessPermissionController;
import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import cn.ibestcode.easiness.auth.query.EasinessPermissionQueryVo;
import cn.ibestcode.easiness.core.paging.DefaultPageableGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/28 19:11
 */
@RestController
@RequestMapping("/api/easiness/permission")
@Api(tags = "权限管理")
public class EasinessShiroPermissionController extends EasinessPermissionController {


  // region 权限管理

  @Override
  @PostMapping
  @ApiOperation("添加权限")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void add(@RequestBody EasinessPermission permission) {
    super.add(permission);
  }

  @Override
  @PutMapping
  @ApiOperation("修改权限")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void edit(@RequestBody EasinessPermission permission) {
    super.edit(permission);
  }

  @Override
  @DeleteMapping("{code}")
  @ApiOperation("删除权限")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void remove(@PathVariable String code) {
    super.remove(code);
  }

  @Override
  @GetMapping("all")
  @ApiOperation("获取所有权限-不分页")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessPermission> getAll() {
    return super.getAll();
  }

  @Override
  @GetMapping
  @ApiOperation("根据条件搜索权限-带分页")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public Page<EasinessPermission> getPage(EasinessPermissionQueryVo filterGenerator, DefaultPageableGenerator pageableGenerator) {
    return super.getPage(filterGenerator, pageableGenerator);
  }

  // endregion

  // region 权限关联的用户管理

  @Override
  @PutMapping("{code}/users")
  @ApiOperation("设置“用户-权限”")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void setUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    super.setUsers(code, userIds);
  }

  @Override
  @PostMapping("{code}/users")
  @ApiOperation("添加“用户-权限”")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void addUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    super.addUsers(code, userIds);
  }

  @Override
  @DeleteMapping("{code}/users")
  @ApiOperation("删除“用户-权限”")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void removeUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    super.removeUsers(code, userIds);
  }

  // endregion


  // region 权限关联的角色管理

  @Override
  @GetMapping("{code}/roles")
  @ApiOperation("获取权限直接关联的角色列表")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessRole> getRoles(@PathVariable String code) {
    return super.getRoles(code);
  }

  @Override
  @PutMapping("{code}/roles")
  @ApiOperation("设置“角色-权限”")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void setRoles(@PathVariable String code, @RequestBody List<String> roleCodes) {
    super.setRoles(code, roleCodes);
  }

  @Override
  @PostMapping("{code}/roles")
  @ApiOperation("添加“角色-权限”")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void addRoles(@PathVariable String code, @RequestBody List<String> roleCodes) {
    super.addRoles(code, roleCodes);
  }

  @Override
  @DeleteMapping("{code}/roles")
  @ApiOperation("删除“角色-权限”")
  @RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void removeRoles(@PathVariable String code, @RequestBody List<String> roleCodes) {
    super.removeRoles(code, roleCodes);
  }
  // endregion
}
