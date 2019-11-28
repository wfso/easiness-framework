package cn.ibestcode.easiness.auth.repository;

import cn.ibestcode.easiness.auth.model.EasinessUserExcludePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EasinessUserExcludePermissionRepository extends JpaRepository<EasinessUserExcludePermission, Object> {
  List<EasinessUserExcludePermission> findAllByPermissionCode(String permissionCode);

  List<EasinessUserExcludePermission> findAllByUserId(long userId);

  void deleteAllByPermissionCode(String permissionCode);

  void deleteAllByUserId(long userId);

  void deleteByUserIdAndPermissionCode(long userId, String permissionCode);
}
