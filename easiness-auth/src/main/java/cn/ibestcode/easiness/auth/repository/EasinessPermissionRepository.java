package cn.ibestcode.easiness.auth.repository;


import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.core.base.repository.BaseJpaRepository;

public interface EasinessPermissionRepository extends BaseJpaRepository<EasinessPermission> {
  void deleteByCode(String code);

  EasinessPermission getByCode(String code);

}
