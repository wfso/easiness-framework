package cn.ibestcode.easiness.auth.repository;

import cn.ibestcode.easiness.auth.model.EasinessUserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EasinessUserPermissionRepository extends JpaRepository<EasinessUserPermission, EasinessUserPermission> {
  List<EasinessUserPermission> findAllByPermissionCode(String permissionCode);

  List<EasinessUserPermission> findAllByUserId(long userId);

  void deleteAllByPermissionCode(String permissionCode);

  void deleteAllByUserId(long userId);

  void deleteByUserIdAndPermissionCode(long userId, String permissionCode);
}
