package cn.ibestcode.easiness.core.base.repository;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface UuidBaseJpaRepository<T extends UuidBaseJpaModel> extends BaseJpaRepository<T> {
  T findByUuid(String uuid);

  @Transactional
  void deleteByUuid(String uuid);
}
