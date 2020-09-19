package cn.ibestcode.easiness.core.base.service;

import cn.ibestcode.easiness.core.base.model.UuidModel;

public interface UuidService<T extends UuidModel> {
  // 无锁读
  T getByUuid(String uuid);

  // 共享锁读
  T readByUuid(String uuid);

  T removeByUuid(String uuid);

  T updateByUuid(T entity);

  T updateByUuidIgnoreNull(T entity);

  T updateByUuidIgnoreEmpty(T entity);
}
