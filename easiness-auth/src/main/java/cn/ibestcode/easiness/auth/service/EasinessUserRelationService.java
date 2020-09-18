package cn.ibestcode.easiness.auth.service;

import cn.ibestcode.easiness.auth.model.*;
import cn.ibestcode.easiness.auth.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class EasinessUserRelationService {

  @Autowired
  private EasinessRoleRepository roleRepository;

  @Autowired
  private EasinessUserRoleRepository userRoleRepository;

  @Autowired
  private EasinessPermissionRepository permissionRepository;

  @Autowired
  private EasinessUserPermissionRepository userPermissionRepository;

  @Autowired
  private EasinessUserExcludePermissionRepository excludePermissionRepository;

  // region 用户的角色管理

  /**
   * 通过用户 ID 获取用户的角色列表
   *
   * @param userId 用户ID
   * @return 角色列表
   */
  public List<EasinessRole> getRolesByUserId(long userId) {
    List<EasinessRole> roles = new ArrayList<>();
    List<EasinessUserRole> userRoles = userRoleRepository.findAllByUserId(userId);
    for (EasinessUserRole userRole : userRoles) {
      EasinessRole role = roleRepository.getByCode(userRole.getRoleCode());
      if (role != null) {
        roles.add(role);
      }
    }
    return roles;
  }

  /**
   * 通过用户 ID 获取用户的角色 Code 列表
   *
   * @param userId 用户ID
   * @return 角色的 Code 列表
   */
  public List<String> getRoleCodesByUserId(long userId) {
    List<String> roleCodes = new ArrayList<>();
    List<EasinessUserRole> userRoles = userRoleRepository.findAllByUserId(userId);
    for (EasinessUserRole userRole : userRoles) {
      roleCodes.add(userRole.getRoleCode());
    }
    return roleCodes;
  }


  /**
   * 设置 用户的角色（用户-角色）
   *
   * @param userId    用户ID
   * @param roleCodes 角色 code 的列表
   */
  @Transactional
  public void setRoles(long userId, List<String> roleCodes) {
    List<String> oldRoleCodes = getRoleCodesByUserId(userId);
    List<String> rmCodes = new ArrayList<>();
    for (String code : oldRoleCodes) {
      if (!roleCodes.contains(code)) {
        rmCodes.add(code);
        userRoleRepository.deleteByUserIdAndRoleCode(userId, code);
      }
    }
    oldRoleCodes.removeAll(rmCodes);
    for (String code : roleCodes) {
      if (!oldRoleCodes.contains(code)) {
        userRoleRepository.save(new EasinessUserRole(userId, code));
      }
    }
  }

  /**
   * 添加 用户的角色（用户-角色）
   *
   * @param userId    用户ID
   * @param roleCodes 角色 code 的列表
   */
  @Transactional
  public void addRoles(long userId, List<String> roleCodes) {
    List<String> oldRoleCodes = getRoleCodesByUserId(userId);
    for (String code : roleCodes) {
      if (!oldRoleCodes.contains(code)) {
        userRoleRepository.save(new EasinessUserRole(userId, code));
      }
    }
  }

  /**
   * 删除 用户的角色（用户-角色）
   *
   * @param userId    用户ID
   * @param roleCodes 角色 code 的列表
   */
  @Transactional
  public void removeRoles(long userId, List<String> roleCodes) {
    for (String code : roleCodes) {
      userRoleRepository.deleteByUserIdAndRoleCode(userId, code);
    }
  }

  // endregion


  // region 用户的权限管理

  /**
   * 通过用户 ID 获取用户的权限列表
   *
   * @param userId 用户的ID
   * @return 权限列表
   */
  public List<EasinessPermission> getPermissionsByUserId(long userId) {
    List<EasinessPermission> permissions = new ArrayList<>();
    List<EasinessUserPermission> userPermissions = userPermissionRepository.findAllByUserId(userId);
    for (EasinessUserPermission userPermission : userPermissions) {
      EasinessPermission permission = permissionRepository.getByCode(userPermission.getPermissionCode());
      if (permission != null) {
        permissions.add(permission);
      }
    }
    return permissions;
  }

  /**
   * 通过用户 ID 获取用记的权限 Code 列表
   *
   * @param userId 用户的ID
   * @return 权限 Code 列表
   */
  public List<String> getPermissionCodesByUserId(long userId) {
    List<String> permissionCodes = new ArrayList<>();
    List<EasinessUserPermission> userPermissions = userPermissionRepository.findAllByUserId(userId);
    for (EasinessUserPermission userPermission : userPermissions) {
      permissionCodes.add(userPermission.getPermissionCode());
    }
    return permissionCodes;
  }


  /**
   * 设置 用户的权限（用户-权限）
   *
   * @param userId          用户ID
   * @param permissionCodes 权限 code 的列表
   */
  @Transactional
  public void setPermissions(long userId, List<String> permissionCodes) {
    List<String> oldPermissionCodes = getPermissionCodesByUserId(userId);
    List<String> rmCodes = new ArrayList<>();
    for (String code : oldPermissionCodes) {
      if (!permissionCodes.contains(code)) {
        rmCodes.add(code);
        userPermissionRepository.deleteByUserIdAndPermissionCode(userId, code);
      }
    }
    oldPermissionCodes.removeAll(rmCodes);
    for (String code : permissionCodes) {
      if (!oldPermissionCodes.contains(code)) {
        userPermissionRepository.save(new EasinessUserPermission(userId, code));
      }
    }
  }

  /**
   * 添加 用户的权限（用户-权限）
   *
   * @param userId          用户ID
   * @param permissionCodes 权限 code 的列表
   */
  @Transactional
  public void addPermissions(long userId, List<String> permissionCodes) {
    List<String> oldPermissionCodes = getPermissionCodesByUserId(userId);
    for (String code : permissionCodes) {
      if (!oldPermissionCodes.contains(code)) {
        userPermissionRepository.save(new EasinessUserPermission(userId, code));
      }
    }
  }

  /**
   * 删除 用户的权限（用户-权限）
   *
   * @param userId          用户ID
   * @param permissionCodes 权限 code 的列表
   */
  @Transactional
  public void removePermissions(long userId, List<String> permissionCodes) {
    for (String code : permissionCodes) {
      userPermissionRepository.deleteByUserIdAndPermissionCode(userId, code);
    }
  }

  // endregion


  // region 用户排除的权限管理

  /**
   * 通过用户 ID 获取用户排除的权限列表
   *
   * @param userId 用户的ID
   * @return 权限列表
   */
  public List<EasinessPermission> getExcludePermissionsByUserId(long userId) {
    List<EasinessPermission> permissions = new ArrayList<>();
    List<EasinessUserExcludePermission> excludePermissions = excludePermissionRepository.findAllByUserId(userId);
    for (EasinessUserExcludePermission excludePermission : excludePermissions) {
      EasinessPermission permission = permissionRepository.getByCode(excludePermission.getPermissionCode());
      if (permission != null) {
        permissions.add(permission);
      }
    }
    return permissions;
  }

  /**
   * 通过用户 ID 获取用户排除的权限 Code 列表
   *
   * @param userId 用户的ID
   * @return 权限 Code 列表
   */
  public List<String> getExcludePermissionCodesByUserId(long userId) {
    List<String> permissionCodes = new ArrayList<>();
    List<EasinessUserExcludePermission> excludePermissions = excludePermissionRepository.findAllByUserId(userId);
    for (EasinessUserExcludePermission excludePermission : excludePermissions) {
      permissionCodes.add(excludePermission.getPermissionCode());
    }
    return permissionCodes;
  }


  /**
   * 设置 用户的 排除权限（用户-排除-权限）
   *
   * @param userId          用户ID
   * @param permissionCodes 权限 code 的列表
   */
  @Transactional
  public void setExcludePermissions(long userId, List<String> permissionCodes) {
    List<String> oldExcludePermissionCodes = getExcludePermissionCodesByUserId(userId);
    List<String> rmCodes = new ArrayList<>();
    for (String code : oldExcludePermissionCodes) {
      if (!permissionCodes.contains(code)) {
        rmCodes.add(code);
        excludePermissionRepository.deleteByUserIdAndPermissionCode(userId, code);
      }
    }
    oldExcludePermissionCodes.removeAll(rmCodes);
    for (String code : permissionCodes) {
      if (!oldExcludePermissionCodes.contains(code)) {
        excludePermissionRepository.save(new EasinessUserExcludePermission(userId, code));
      }
    }
  }

  /**
   * 添加 用户的 排除权限（用户-排除-权限）
   *
   * @param userId          用户ID
   * @param permissionCodes 权限 code 的列表
   */
  @Transactional
  public void addExcludePermissions(long userId, List<String> permissionCodes) {
    List<String> oldExcludePermissionCodes = getExcludePermissionCodesByUserId(userId);
    for (String code : permissionCodes) {
      if (!oldExcludePermissionCodes.contains(code)) {
        excludePermissionRepository.save(new EasinessUserExcludePermission(userId, code));
      }
    }
  }

  /**
   * 删除 用户的 排除权限（用户-排除-权限）
   *
   * @param userId          用户ID
   * @param permissionCodes 权限 code 的列表
   */
  @Transactional
  public void removeExcludePermissions(long userId, List<String> permissionCodes) {
    for (String code : permissionCodes) {
      excludePermissionRepository.deleteByUserIdAndPermissionCode(userId, code);
    }
  }

  // endregion

}
