package cn.ibestcode.easiness.auth.repository;

import cn.ibestcode.easiness.auth.model.EasinessRole;
import cn.ibestcode.easiness.core.base.repository.BaseJpaRepository;

public interface EasinessRoleRepository extends BaseJpaRepository<EasinessRole> {
  void deleteByCode(String code);

  EasinessRole getByCode(String code);
}
