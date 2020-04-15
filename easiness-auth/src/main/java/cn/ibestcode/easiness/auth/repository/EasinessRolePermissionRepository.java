package cn.ibestcode.easiness.auth.repository;

import cn.ibestcode.easiness.auth.model.EasinessRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EasinessRolePermissionRepository extends JpaRepository<EasinessRolePermission, EasinessRolePermission> {
  List<EasinessRolePermission> findAllByPermissionCode(String permissionCode);

  List<EasinessRolePermission> findAllByRoleCode(String roleCode);

  void deleteAllByRoleCode(String roleCode);

  void deleteAllByPermissionCode(String permissionCode);

  void deleteByRoleCodeAndPermissionCode(String roleCode, String permissionCode);
}
