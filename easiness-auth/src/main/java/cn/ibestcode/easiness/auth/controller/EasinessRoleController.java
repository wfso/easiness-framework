package cn.ibestcode.easiness.auth.controller;

import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import cn.ibestcode.easiness.auth.query.EasinessRoleQueryVo;
import cn.ibestcode.easiness.auth.service.EasinessRoleService;
import cn.ibestcode.easiness.core.paging.DefaultPageableGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RequestMapping("/api/easiness/role")
public class EasinessRoleController {

  @Autowired
  private EasinessRoleService service;


  // region 角色管理

  @PostMapping
  @ApiOperation("添加角色")
  //@RequiresPermissions("role:add")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void add(@RequestBody EasinessRole role) {
    service.create(role);
  }

  @PutMapping
  @ApiOperation("修改角色")
  //@RequiresPermissions("role:edit")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void edit(@RequestBody EasinessRole role) {
    service.create(role);
  }

  @DeleteMapping("{code}")
  @ApiOperation("删除角色")
  //@RequiresPermissions("role:remove")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void remove(@PathVariable String code) {
    service.removeByCode(code);
  }

  @GetMapping("all")
  @ApiOperation("获取所有角色-不分页")
  //@RequiresPermissions("role:all")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessRole> getAll() {
    return service.getAll();
  }

  @GetMapping
  @ApiOperation("根据条件搜索角色-带分页")
  //@RequiresPermissions("role:list")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public Page<EasinessRole> getPage(EasinessRoleQueryVo filterGenerator, DefaultPageableGenerator pageableGenerator) {
    return service.getPage(filterGenerator.generateFilter(), pageableGenerator.generatePageable());
  }

  // endregion


  // region 角色的权限管理

  @GetMapping("{code}/permissions")
  @ApiOperation("获取角色的权限列表")
  //@RequiresPermissions("role:get:permissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<EasinessPermission > getPermissions(@PathVariable String code) {
    return service.getPermissionsByRoleCode(code);
  }

  @PutMapping("{code}/permissions")
  @ApiOperation("设置“角色-权限”")
  //@RequiresPermissions("role:set:permissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void setPermissions(@PathVariable String code, @RequestBody List<String> permissionCodes) {
    service.setPermissions(code, permissionCodes);
  }

  @PostMapping("{code}/permissions")
  @ApiOperation("添加“角色-权限”")
  //@RequiresPermissions("role:add:permissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void addPermissions(@PathVariable String code, @RequestBody List<String> permissionCodes) {
    service.addPermissions(code, permissionCodes);
  }

  @DeleteMapping("{code}/permissions")
  @ApiOperation("删除“角色-权限”")
  //@RequiresPermissions("role:remove:permissions")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void removePermissions(@PathVariable String code, @RequestBody List<String> permissionCodes) {
    service.removePermissions(code, permissionCodes);
  }

  // endregion

  // region 与角色关系的用户管理

  @PutMapping("{code}/users")
  @ApiOperation("设置“用户-角色”")
  //@RequiresPermissions("role:set:users")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void setUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    service.setUsers(code, userIds);
  }

  @PostMapping("{code}/users")
  @ApiOperation("添加“用户-角色”")
  //@RequiresPermissions("role:add:users")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void addUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    service.addUsers(code, userIds);
  }

  @DeleteMapping("{code}/users")
  @ApiOperation("删除“用户-角色”")
  //@RequiresPermissions("role:remove:users")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void removeUsers(@PathVariable String code, @RequestBody List<Long> userIds) {
    service.removeUsers(code, userIds);
  }


  // endregion
}
