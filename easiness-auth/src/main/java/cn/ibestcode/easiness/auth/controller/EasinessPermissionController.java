package cn.ibestcode.easiness.auth.controller;

import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import cn.ibestcode.easiness.auth.query.EasinessPermissionQueryVo;
import cn.ibestcode.easiness.auth.service.EasinessPermissionService;
import cn.ibestcode.easiness.core.paging.DefaultPageableGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "权限管理")
@RequestMapping("/api/easiness/permission")
public class EasinessPermissionController {

  @Autowired
  private EasinessPermissionService service;


  // region 权限管理

  @PostMapping
  @ApiOperation("添加权限")
  //@RequiresPermissions("permission:add")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void add(@RequestBody EasinessPermission permission) {
    service.create(permission);
  }

  @PutMapping
  @ApiOperation("修改权限")
  //@RequiresPermissions("permission:edit")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void edit(@RequestBody EasinessPermission permission) {
    service.create(permission);
  }

  @DeleteMapping("{code}")
  @ApiOperation("删除权限")
  //@RequiresPermissions("permission:remove")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void remove(@PathVariable String code) {
    service.removeByCode(code);
  }

  @GetMapping("all")
  @ApiOperation("获取所有权限-不分页")
  //@RequiresPermissions("permission:all")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessPermission> getAll() {
    return service.getAll();
  }

  @GetMapping
  @ApiOperation("根据条件搜索权限-带分页")
  //@RequiresPermissions("permission:list")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public Page<EasinessPermission> getPage(EasinessPermissionQueryVo filterGenerator, DefaultPageableGenerator pageableGenerator) {
    return service.getPage(filterGenerator.generateFilter(), pageableGenerator.generatePageable());
  }

  // endregion

  // region 权限关联的用户管理

  @PutMapping("{code}/users")
  @ApiOperation("设置“用户-权限”")
  //@RequiresPermissions("permission:set:user")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void setUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    service.setUsers(code, userIds);
  }

  @PostMapping("{code}/users")
  @ApiOperation("添加“用户-权限”")
  //@RequiresPermissions("permission:add:user")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void addUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    service.addUsers(code, userIds);
  }

  @DeleteMapping("{code}/users")
  @ApiOperation("删除“用户-权限”")
  //@RequiresPermissions("permission:remove:user")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void removeUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    service.removeUsers(code, userIds);
  }

  // endregion


  // region 权限关联的角色管理

  @GetMapping("{code}/roles")
  @ApiOperation("获取权限直接关联的角色列表")
  //@RequiresPermissions("permission:get:roles")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessRole> getRoles(@PathVariable String code) {
    return service.getRolesByPermissionCode(code);
  }

  @PutMapping("{code}/roles")
  @ApiOperation("设置“角色-权限”")
  //@RequiresPermissions("permission:set:roles")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void setRoles(@PathVariable String code, @RequestBody List<String> roleCodes) {
    service.setRoles(code, roleCodes);
  }

  @PostMapping("{code}/roles")
  @ApiOperation("添加“角色-权限”")
  //@RequiresPermissions("permission:add:roles")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void addRoles(@PathVariable String code, @RequestBody List<String> roleCodes) {
    service.addRoles(code, roleCodes);
  }

  @DeleteMapping("{code}/roles")
  @ApiOperation("删除“角色-权限”")
  //@RequiresPermissions("permission:remove:roles")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void removeRoles(@PathVariable String code, @RequestBody List<String> roleCodes) {
    service.removeRoles(code, roleCodes);
  }

  // endregion
}
