package cn.ibestcode.easiness.auth.controller;

import cn.ibestcode.easiness.auth.biz.EasinessUserRelationBiz;
import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import cn.ibestcode.easiness.auth.service.EasinessUserRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理")
@RequestMapping("/api/easiness/user")
public class EasinessUserRelationController {

  @Autowired
  private EasinessUserRelationService service;

  @Autowired
  private EasinessUserRelationBiz biz;

  // region 用户的角色管理

  @GetMapping("{userId}/roles")
  @ApiOperation("获取用户的角色列表")
  //@RequiresPermissions("user:get:roles")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessRole> getRoles(@PathVariable long userId) {
    return service.getRolesByUserId(userId);
  }

  @PutMapping("{userId}/roles")
  @ApiOperation("设置“用户-角色”")
  //@RequiresPermissions("user:set:roles")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void setRoles(@PathVariable long userId, @RequestBody List<String> roleCodes) {
    service.setRoles(userId, roleCodes);
  }

  @PostMapping("{userId}/roles")
  @ApiOperation("添加“用户-角色”")
  //@RequiresPermissions("user:add:roles")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void addRoles(@PathVariable long userId, @RequestBody List<String> roleCodes) {
    service.addRoles(userId, roleCodes);
  }

  @DeleteMapping("{userId}/roles")
  @ApiOperation("删除“用户-角色”")
  //@RequiresPermissions("user:remove:roles")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void removeRoles(@PathVariable long userId, @RequestBody List<String> roleCodes) {
    service.removeRoles(userId, roleCodes);
  }


  // endregion

  // region 用户的权限管理

  @GetMapping("{userId}/permissions")
  @ApiOperation("获取用户的权限列表")
  //@RequiresPermissions("user:get:permissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessPermission> getPermissions(@PathVariable long userId) {
    return service.getPermissionsByUserId(userId);
  }

  @PutMapping("{userId}/permissions")
  @ApiOperation("设置“用户-权限”")
  //@RequiresPermissions("user:set:permissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void setPermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    service.setPermissions(userId, permissionCodes);
  }

  @PostMapping("{userId}/permissions")
  @ApiOperation("添加“用户-权限”")
  //@RequiresPermissions("user:add:permissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void addPermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    service.addPermissions(userId, permissionCodes);
  }

  @DeleteMapping("{userId}/permissions")
  @ApiOperation("删除“用户-权限”")
  //@RequiresPermissions("user:remove:permissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void removePermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    service.removePermissions(userId, permissionCodes);
  }

  // endregion

  // region 用户的排除权限管理

  @GetMapping("{userId}/excludePermissions")
  @ApiOperation("获取用户的排除权限列表")
  //@RequiresPermissions("user:get:excludePermissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessPermission> getExcludePermissions(@PathVariable long userId) {
    return service.getExcludePermissionsByUserId(userId);
  }

  @PutMapping("{userId}/excludePermissions")
  @ApiOperation("设置“用户-排除-权限”")
  //@RequiresPermissions("user:set:excludePermissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void setExcludePermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    service.setExcludePermissions(userId, permissionCodes);
  }

  @PostMapping("{userId}/excludePermissions")
  @ApiOperation("添加“用户-排除-权限”")
  //@RequiresPermissions("user:add:excludePermissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void addExcludePermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    service.addExcludePermissions(userId, permissionCodes);
  }

  @DeleteMapping("{userId}/excludePermissions")
  @ApiOperation("删除“用户-排除-权限”")
  //@RequiresPermissions("user:remove:excludePermissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void removeExcludePermissions(@PathVariable long userId, @RequestBody List<String> permissionCodes) {
    service.removeExcludePermissions(userId, permissionCodes);
  }

  // endregion

  // region 用户的所有权限
  // 包括从角色关系过来

  @GetMapping("{userId}/availablePermissions")
  @ApiOperation("获取用户有效权限列表")
  //@RequiresPermissions("user:available:permissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessPermission> getAvailablePermissions(@PathVariable long userId) {
    return biz.getAvailablePermissionsByUserId(userId);
  }

  // endregion

}
