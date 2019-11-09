package cn.ibestcode.easiness.core.base.repository;

import cn.ibestcode.easiness.core.base.model.BaseJpaModel;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseJpaModel> extends Repository<T, Long> {
}
