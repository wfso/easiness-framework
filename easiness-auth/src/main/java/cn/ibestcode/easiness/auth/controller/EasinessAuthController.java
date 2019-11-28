package cn.ibestcode.easiness.auth.controller;

import cn.ibestcode.easiness.auth.biz.EasinessAuthBiz;
import cn.ibestcode.easiness.auth.domain.EasinessLoginBeforeResult;
import cn.ibestcode.easiness.auth.domain.EasinessLoginResult;
import cn.ibestcode.easiness.auth.domain.RoleAndPermissionResult;
import cn.ibestcode.easiness.auth.model.EasinessPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "身份认证及授权接口")
public class EasinessAuthController {

  @Autowired
  private EasinessAuthBiz easinessAuthBiz;

  @PostMapping("loginBefore")
  @ApiOperation("登录接口")
  public EasinessLoginBeforeResult beforeLogin(@PathVariable String username) {
    return easinessAuthBiz.loginBefore(username);
  }

  @GetMapping("checkLogin")
  @ApiOperation("检测登录")
  public EasinessLoginResult checkLogin() {
    return easinessAuthBiz.checkLogin();
  }

  @GetMapping("checkRole")
  @ApiOperation("检测/选择 主角色")
  public RoleAndPermissionResult checkRole(@PathVariable String roleCode) {
    return easinessAuthBiz.checkRole(roleCode);
  }

  @PostMapping("login/{type}")
  @ApiOperation("登录接口")
  public EasinessLoginResult login(@PathVariable String type) {
    return easinessAuthBiz.login(type);
  }

  @PostMapping("logout")
  @ApiOperation("退出接口")
  public void logout() {
    easinessAuthBiz.logout();
  }

  @GetMapping("roleCodes")
  @ApiOperation("获取当前用户的角色 Code 列表")
  public List<String> getRoleCodes() {
    return easinessAuthBiz.getLoginRoleCodes();
  }

  @GetMapping("availablePermissionCodes")
  @ApiOperation("获取当前用户有效权限 Code 列表")
  public List<String> getAvailablePermissionCodes() {
    return easinessAuthBiz.getAvailablePermissionCodes();
  }

  @GetMapping("currentPermissionCodes")
  @ApiOperation("获取当前用户有效权限 Code 列表")
  public List<String> getCurrentPermissionCodes() {
    return easinessAuthBiz.getCurrentPermissionCodes();
  }

  @GetMapping("availablePermissions")
  @ApiOperation("获取当前用户有效权限 列表")
  public List<EasinessPermission> getAvailablePermissions() {
    return easinessAuthBiz.getAvailablePermissions();
  }

  @GetMapping("currentPermissions")
  @ApiOperation("获取当前用户有效权限 列表")
  public List<EasinessPermission> getCurrentPermissions() {
    return easinessAuthBiz.getCurrentPermissions();
  }

}
