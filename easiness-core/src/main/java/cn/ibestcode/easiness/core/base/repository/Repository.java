package cn.ibestcode.easiness.core.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

@NoRepositoryBean
public interface Repository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
  @Transactional
  @Lock(LockModeType.PESSIMISTIC_READ)
  T readById(ID id);
}
