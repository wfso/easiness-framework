package cn.ibestcode.easiness.auth.service;

import cn.ibestcode.easiness.auth.model.*;
import cn.ibestcode.easiness.auth.repository.*;
import cn.ibestcode.easiness.core.base.service.BaseJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EasinessRoleService extends BaseJpaService<EasinessRole> {

  @Autowired
  private EasinessRoleRepository repository;

  @Autowired
  private EasinessPermissionRepository permissionRepository;

  @Autowired
  private EasinessRolePermissionRepository rolePermissionRepository;

  @Autowired
  private EasinessUserRoleRepository userRoleRepository;


  @Override
  protected EasinessRoleRepository getRepository() {
    return repository;
  }


  public EasinessRole getById(long id) {
    return repository.findById(id).orElse(null);
  }


  public EasinessRole getByCode(String code) {
    return repository.getByCode(code);
  }


  // region 删除角色的方法
  // 加入删除 “角色-权限” 和 “用户-角色”的逻辑

  @Transactional
  public EasinessRole removeByCode(String code) {
    rolePermissionRepository.deleteAllByRoleCode(code);
    userRoleRepository.deleteAllByRoleCode(code);
    EasinessRole role = getByCode(code);
    repository.deleteByCode(code);
    return role;
  }

  @Override
  @Transactional
  public EasinessRole removeById(Long aLong) {
    EasinessRole role = getById(aLong);
    if (role != null) {
      rolePermissionRepository.deleteAllByRoleCode(role.getCode());
      userRoleRepository.deleteAllByRoleCode(role.getCode());
    }
    return super.removeById(aLong);
  }

  @Override
  @Transactional
  public Iterable<EasinessRole> remove(Iterable<EasinessRole> entities) {
    for (EasinessRole role : entities) {
      rolePermissionRepository.deleteAllByRoleCode(role.getCode());
      userRoleRepository.deleteAllByRoleCode(role.getCode());
    }
    return super.remove(entities);
  }

  @Override
  @Transactional
  public EasinessRole remove(EasinessRole entity) {
    rolePermissionRepository.deleteAllByRoleCode(entity.getCode());
    userRoleRepository.deleteAllByRoleCode(entity.getCode());
    return super.remove(entity);
  }

  // endregion

  // region 角色的权限管理

  /**
   * 通过角色 Code 获取与角色关联的权限列表
   *
   * @param roleCode 角色的Code
   * @return 权限列表
   */
  public List<EasinessPermission> getPermissionsByRoleCode(String roleCode) {
    List<EasinessPermission> permissions = new ArrayList<>();
    List<EasinessRolePermission> rolePermissions = rolePermissionRepository.findAllByRoleCode(roleCode);
    for (EasinessRolePermission rolePermission : rolePermissions) {
      EasinessPermission permission = permissionRepository.getByCode(rolePermission.getPermissionCode());
      if (permission != null) {
        permissions.add(permission);
      }
    }
    return permissions;
  }

  /**
   * 通过角色 Code 获取与角色关联的权限 Code 列表
   *
   * @param roleCode 角色的 Code
   * @return 权限 Code 列表
   */
  public List<String> getPermissionCodesByRoleCode(String roleCode) {
    List<String> permissionCodes = new ArrayList<>();
    List<EasinessRolePermission> rolePermissions = rolePermissionRepository.findAllByRoleCode(roleCode);
    for (EasinessRolePermission rolePermission : rolePermissions) {
      permissionCodes.add(rolePermission.getPermissionCode());
    }
    return permissionCodes;
  }

  /**
   * 设置 角色与权限 的关联关系 （角色-权限）
   *
   * @param roleCode        角色 code
   * @param permissionCodes 权限 code 的列表
   */
  @Transactional
  public void setPermissions(String roleCode, List<String> permissionCodes) {
    List<String> oldPermissionCodes = getPermissionCodesByRoleCode(roleCode);
    List<String> rmCodes = new ArrayList<>();
    for (String code : oldPermissionCodes) {
      if (!permissionCodes.contains(code)) {
        rmCodes.add(code);
        rolePermissionRepository.deleteByRoleCodeAndPermissionCode(roleCode, code);
      }
    }
    oldPermissionCodes.removeAll(rmCodes);
    for (String code : permissionCodes) {
      if (!oldPermissionCodes.contains(code)) {
        rolePermissionRepository.save(new EasinessRolePermission(roleCode, code));
      }
    }
  }

  /**
   * 添加 角色与权限 的关联关系 （角色-权限）
   *
   * @param roleCode        角色 code
   * @param permissionCodes 权限 code 的列表
   */
  @Transactional
  public void addPermissions(String roleCode, List<String> permissionCodes) {
    List<String> oldPermissionCodes = getPermissionCodesByRoleCode(roleCode);
    for (String code : permissionCodes) {
      if (!oldPermissionCodes.contains(code)) {
        rolePermissionRepository.save(new EasinessRolePermission(roleCode, code));
      }
    }
  }

  /**
   * 删除 角色与权限 的关联关系 （角色-权限）
   *
   * @param roleCode        角色 code
   * @param permissionCodes 权限 code 的列表
   */
  @Transactional
  public void removePermissions(String roleCode, List<String> permissionCodes) {
    for (String code : permissionCodes) {
      rolePermissionRepository.deleteByRoleCodeAndPermissionCode(roleCode, code);
    }
  }

  // endregion

  // region 角色关联的用户管理

  /**
   * 通过角色 Code 获取与角色关联的用户 id 列表
   *
   * @param roleCode 角色的 Code
   * @return 用户 id 列表
   */
  public List<Long> getUserIdsByRoleCode(String roleCode) {
    List<Long> userIds = new ArrayList<>();
    List<EasinessUserRole> userRoles = userRoleRepository.findAllByRoleCode(roleCode);
    for (EasinessUserRole userRole : userRoles) {
      userIds.add(userRole.getUserId());
    }
    return userIds;
  }


  /**
   * 设置 用户与角色 的关联关系（用户-角色）
   *
   * @param roleCode 角色 code
   * @param userIds  用户 id 列表
   */
  @Transactional
  public void setUsers(String roleCode, List<Long> userIds) {
    List<Long> oldUserIds = getUserIdsByRoleCode(roleCode);
    List<Long> rmIds = new ArrayList<>();
    for (long id : oldUserIds) {
      if (!userIds.contains(id)) {
        rmIds.add(id);
        userRoleRepository.deleteByUserIdAndRoleCode(id, roleCode);
      }
    }
    oldUserIds.removeAll(rmIds);
    for (long id : userIds) {
      if (!oldUserIds.contains(id)) {
        userRoleRepository.save(new EasinessUserRole(id, roleCode));
      }
    }
  }

  /**
   * 添加 用户与角色 的关联关系（用户-角色）
   *
   * @param roleCode 角色 code
   * @param userIds  用户 id 列表
   */
  @Transactional
  public void addUsers(String roleCode, List<Long> userIds) {
    List<Long> oldUserIds = getUserIdsByRoleCode(roleCode);
    for (long id : userIds) {
      if (!oldUserIds.contains(id)) {
        userRoleRepository.save(new EasinessUserRole(id, roleCode));
      }
    }
  }

  /**
   * 删除 用户与角色 的关联关系（用户-角色）
   *
   * @param roleCode 角色 code
   * @param userIds  用户 id 列表
   */
  @Transactional
  public void removeUsers(String roleCode, List<Long> userIds) {
    for (long id : userIds) {
      userRoleRepository.deleteByUserIdAndRoleCode(id, roleCode);
    }
  }

  // endregion

}
