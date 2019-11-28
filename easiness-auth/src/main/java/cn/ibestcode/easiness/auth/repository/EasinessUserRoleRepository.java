package cn.ibestcode.easiness.auth.repository;

import cn.ibestcode.easiness.auth.model.EasinessUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EasinessUserRoleRepository extends JpaRepository<EasinessUserRole, Object> {
  List<EasinessUserRole> findAllByUserId(long userId);

  List<EasinessUserRole> findAllByRoleCode(String roleCode);

  void deleteAllByUserId(long userId);

  void deleteAllByRoleCode(String roleCode);

  void deleteByUserIdAndRoleCode(long userId, String roleCode);
}
