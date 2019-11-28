package cn.ibestcode.easiness.auth.biz;

import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.service.EasinessRoleService;
import cn.ibestcode.easiness.auth.service.EasinessUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EasinessUserRelationBiz {

  @Autowired
  private EasinessUserRelationService userRelationService;

  @Autowired
  private EasinessRoleService roleService;

  /**
   * 通过用户 ID 获取用户的所有权限列表（包含通过角色间接关联的权限）
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  public List<EasinessPermission> getAvailablePermissionsByUserId(long userId) {
    List<EasinessPermission> permissions = userRelationService.getPermissionsByUserId(userId);
    List<String> roleCodes = userRelationService.getRoleCodesByUserId(userId);
    for (String roleCode : roleCodes) {
      List<EasinessPermission> rolePermissions = roleService.getPermissionsByRoleCode(roleCode);
      permissions.addAll(rolePermissions);
    }
    List<String> excludePermissionCodes = userRelationService.getExcludePermissionCodesByUserId(userId);
    List<EasinessPermission> result = new ArrayList<>();
    for (EasinessPermission permission : permissions) {
      if (!excludePermissionCodes.contains(permission.getCode())) {
        result.add(permission);
      }
    }
    return result;
  }

  /**
   * 通过用户 ID 获取用户的所有权限 Code 列表（包含通过角色间接关联的权限）
   *
   * @param userId 用户ID
   * @return 权限 Code 列表
   */
  public List<String> getAvailablePermissionCodesByUserId(long userId) {
    List<String> permissionCodes = userRelationService.getPermissionCodesByUserId(userId);
    List<String> roleCodes = userRelationService.getRoleCodesByUserId(userId);
    for (String roleCode : roleCodes) {
      List<String> rolePermissionCodes = roleService.getPermissionCodesByRoleCode(roleCode);
      permissionCodes.addAll(rolePermissionCodes);
    }
    List<String> excludePermissionCodes = userRelationService.getExcludePermissionCodesByUserId(userId);
    permissionCodes.removeAll(excludePermissionCodes);
    return permissionCodes;
  }
}
