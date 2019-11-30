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
public class EasinessPermissionService extends BaseJpaService<EasinessPermission> {

  @Autowired
  private EasinessPermissionRepository repository;

  @Autowired
  private EasinessRolePermissionRepository rolePermissionRepository;

  @Autowired
  private EasinessRoleRepository roleRepository;

  @Autowired
  private EasinessUserPermissionRepository userPermissionRepository;


  @Override
  protected EasinessPermissionRepository getRepository() {
    return repository;
  }


  public EasinessPermission getById(long id) {
    return repository.findById(id).orElse(null);
  }


  public EasinessPermission getByCode(String code) {
    return repository.getByCode(code);
  }

  // region 删除权限的方法
  // 加入删除 “用户-权限” 和 “角色-权限”的逻辑

  @Transactional
  public EasinessPermission removeByCode(String code) {
    userPermissionRepository.deleteAllByPermissionCode(code);
    rolePermissionRepository.deleteAllByPermissionCode(code);
    EasinessPermission permission = getByCode(code);
    repository.deleteByCode(code);
    return permission;
  }

  @Override
  @Transactional
  public EasinessPermission removeById(Long aLong) {
    EasinessPermission permission = getById(aLong);
    if (permission != null) {
      userPermissionRepository.deleteAllByPermissionCode(permission.getCode());
      rolePermissionRepository.deleteAllByPermissionCode(permission.getCode());
    }
    return super.removeById(aLong);
  }

  @Override
  @Transactional
  public Iterable<EasinessPermission> remove(Iterable<EasinessPermission> entities) {
    for (EasinessPermission permission : entities) {
      userPermissionRepository.deleteAllByPermissionCode(permission.getCode());
      rolePermissionRepository.deleteAllByPermissionCode(permission.getCode());
    }
    return super.remove(entities);
  }

  @Override
  @Transactional
  public EasinessPermission remove(EasinessPermission entity) {
    userPermissionRepository.deleteAllByPermissionCode(entity.getCode());
    rolePermissionRepository.deleteAllByPermissionCode(entity.getCode());
    return super.remove(entity);
  }

  // endregion

  // region 权限关联的角色管理

  /**
   * 通过权限 Code 获取所有与其关联的角色列表
   *
   * @param permissionCode 权限 Code
   * @return 角色列表
   */
  public List<EasinessRole> getRolesByPermissionCode(String permissionCode) {
    List<EasinessRole> roles = new ArrayList<>();
    List<EasinessRolePermission> rolePermissions = rolePermissionRepository.findAllByPermissionCode(permissionCode);
    for (EasinessRolePermission rolePermission : rolePermissions) {
      EasinessRole role = roleRepository.getByCode(rolePermission.getRoleCode());
      if (role != null) {
        roles.add(role);
      }
    }
    return roles;
  }

  /**
   * 通过权限 Code 获取所有与其关联的角色 Code 列表
   *
   * @param permissionCode 权限 Code
   * @return 角色 Code 列表
   */
  public List<String> getRoleCodesByPermissionCode(String permissionCode) {
    List<String> roleCodes = new ArrayList<>();
    List<EasinessRolePermission> rolePermissions = rolePermissionRepository.findAllByPermissionCode(permissionCode);
    for (EasinessRolePermission rolePermission : rolePermissions) {
      roleCodes.add(rolePermission.getRoleCode());
    }
    return roleCodes;
  }

  /**
   * 设置 角色与权限 的关联关系 （角色-权限）
   *
   * @param permissionCode 权限 code
   * @param roleCodes      角色 code 的列表
   */
  @Transactional
  public void setRoles(String permissionCode, List<String> roleCodes) {
    List<String> oldRoleCodes = getRoleCodesByPermissionCode(permissionCode);
    List<String> rmCodes = new ArrayList<>();
    for (String code : oldRoleCodes) {
      if (!roleCodes.contains(code)) {
        rmCodes.add(code);
        rolePermissionRepository.deleteByRoleCodeAndPermissionCode(code, permissionCode);
      }
    }
    oldRoleCodes.removeAll(rmCodes);
    for (String code : roleCodes) {
      if (!oldRoleCodes.contains(code)) {
        rolePermissionRepository.save(new EasinessRolePermission(code, permissionCode));
      }
    }
  }

  /**
   * 添加 角色与权限 的关联关系 （角色-权限）
   *
   * @param permissionCode 权限 code
   * @param roleCodes      角色 code 的列表
   */
  @Transactional
  public void addRoles(String permissionCode, List<String> roleCodes) {
    List<String> oldRoleCodes = getRoleCodesByPermissionCode(permissionCode);
    for (String code : roleCodes) {
      if (!oldRoleCodes.contains(code)) {
        rolePermissionRepository.save(new EasinessRolePermission(code, permissionCode));
      }
    }
  }

  /**
   * 删除 角色与权限 的关联关系 （角色-权限）
   *
   * @param permissionCode 权限 code
   * @param roleCodes      角色 code 的列表
   */
  @Transactional
  public void removeRoles(String permissionCode, List<String> roleCodes) {
    for (String code : roleCodes) {
      rolePermissionRepository.deleteByRoleCodeAndPermissionCode(code, permissionCode);
    }
  }

  // endregion

  // region 权限关联的用户管理


  /**
   * 通过权限 Code 获取所有与其直接关联的用户 id 列表
   *
   * @param permissionCode 权限 Code
   * @return 用户 id 列表
   */
  public List<Long> getUserIdsByPermissionCode(String permissionCode) {
    List<Long> userIds = new ArrayList<>();
    List<EasinessUserPermission> userPermissions = userPermissionRepository.findAllByPermissionCode(permissionCode);
    for (EasinessUserPermission userPermission : userPermissions) {
      userIds.add(userPermission.getUserId());
    }
    return userIds;
  }

  /**
   * 设置 用户与权限 的关联关系 （用户-权限）
   *
   * @param permissionCode 权限 code
   * @param userIds        用户 id 的列表
   */
  @Transactional
  public void setUsers(String permissionCode, List<Long> userIds) {
    List<Long> oldUserIds = getUserIdsByPermissionCode(permissionCode);
    List<Long> rmIds = new ArrayList<>();
    for (long id : oldUserIds) {
      if (!userIds.contains(id)) {
        rmIds.add(id);
        userPermissionRepository.deleteByUserIdAndPermissionCode(id, permissionCode);
      }
    }
    oldUserIds.removeAll(rmIds);
    for (long id : userIds) {
      if (!oldUserIds.contains(id)) {
        userPermissionRepository.save(new EasinessUserPermission(id, permissionCode));
      }
    }
  }

  /**
   * 添加 用户与权限 的关联关系 （用户-权限）
   *
   * @param permissionCode 权限 code
   * @param userIds        用户 id 的列表
   */
  @Transactional
  public void addUsers(String permissionCode, List<Long> userIds) {
    List<Long> oldUserIds = getUserIdsByPermissionCode(permissionCode);
    for (long id : userIds) {
      if (!oldUserIds.contains(id)) {
        userPermissionRepository.save(new EasinessUserPermission(id, permissionCode));
      }
    }
  }

  /**
   * 删除 用户与权限 的关联关系 （用户-权限）
   *
   * @param permissionCode 权限 code
   * @param userIds        用户 id 的列表
   */
  @Transactional
  public void removeUsers(String permissionCode, List<Long> userIds) {
    for (long id : userIds) {
      userPermissionRepository.deleteByUserIdAndPermissionCode(id, permissionCode);
    }
  }

  // endregion
}
