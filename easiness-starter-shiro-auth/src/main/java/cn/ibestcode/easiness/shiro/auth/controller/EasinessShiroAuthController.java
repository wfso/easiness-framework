/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.shiro.auth.controller;

import cn.ibestcode.easiness.auth.controller.EasinessAuthController;
import cn.ibestcode.easiness.auth.domain.EasinessLoginBeforeResult;
import cn.ibestcode.easiness.auth.domain.EasinessLoginResult;
import cn.ibestcode.easiness.auth.domain.RoleAndPermissionResult;
import cn.ibestcode.easiness.auth.model.EasinessPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/easiness/auth")
@Api(tags = "身份认证及授权接口")
public class EasinessShiroAuthController extends EasinessAuthController {

  @PostMapping("loginBefore")
  @ApiOperation("登录前获取加密参数的接口")
  public EasinessLoginBeforeResult beforeLogin(@PathVariable String username) {
    return super.beforeLogin(username);
  }

  @GetMapping("checkLogin")
  @ApiOperation("检测登录")
  @RequiresAuthentication
  public EasinessLoginResult checkLogin() {
    return super.checkLogin();
  }

  @GetMapping("checkRole")
  @ApiOperation("检测/选择 主角色")
  @RequiresAuthentication
  public RoleAndPermissionResult checkRole(@PathVariable String roleCode) {
    return super.checkRole(roleCode);
  }

  @PostMapping("login/{type}")
  @ApiOperation("登录接口")
  public EasinessLoginResult login(@PathVariable String type) {
    return super.login(type);
  }

  @PostMapping("logout")
  @ApiOperation("退出接口")
  @RequiresAuthentication
  public void logout() {
    super.logout();
  }

  @GetMapping("roleCodes")
  @ApiOperation("获取当前用户的角色 Code 列表")
  @RequiresAuthentication
  public List<String> getRoleCodes() {
    return super.getRoleCodes();
  }

  @GetMapping("availablePermissionCodes")
  @ApiOperation("获取当前用户有效权限 Code 列表")
  @RequiresAuthentication
  public List<String> getAvailablePermissionCodes() {
    return super.getAvailablePermissionCodes();
  }

  @GetMapping("currentPermissionCodes")
  @ApiOperation("获取当前用户有效权限 Code 列表")
  @RequiresAuthentication
  public List<String> getCurrentPermissionCodes() {
    return super.getCurrentPermissionCodes();
  }

  @GetMapping("availablePermissions")
  @ApiOperation("获取用户有效权限 列表")
  @RequiresAuthentication
  public List<EasinessPermission> getAvailablePermissions() {
    return super.getAvailablePermissions();
  }

  @GetMapping("currentPermissions")
  @ApiOperation("获取用户当前权限 列表")
  @RequiresAuthentication
  public List<EasinessPermission> getCurrentPermissions() {
    return super.getCurrentPermissions();
  }

}
