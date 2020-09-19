package cn.ibestcode.easiness.core.base.repository;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

@NoRepositoryBean
public interface UuidBaseJpaRepository<T extends UuidBaseJpaModel> extends BaseJpaRepository<T> {
  T findByUuid(String uuid);

  @Transactional
  @Lock(LockModeType.PESSIMISTIC_READ)
  T readByUuid(String uuid);

  @Transactional
  void deleteByUuid(String uuid);
}
