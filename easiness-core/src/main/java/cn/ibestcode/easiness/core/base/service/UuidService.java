package cn.ibestcode.easiness.core.base.service;

import cn.ibestcode.easiness.core.base.model.UuidModel;

public interface UuidService<T extends UuidModel> {
  T getByUuid(String uuid);

  T removeByUuid(String uuid);

  T updateByUuid(T entity);

  T updateByUuidIgnoreNull(T entity);

  T updateByUuidIgnoreEmpty(T entity);
}
