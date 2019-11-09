package cn.ibestcode.easiness.core.base.service;


import cn.ibestcode.easiness.core.query.builder.DefaultFiltersBuilder;
import cn.ibestcode.easiness.core.query.filter.IFilter;

import java.util.List;

public abstract class UuidBaseMyBatisService<T> extends BaseMyBatisService<T, Long> {

  protected abstract String getUuidFieldName();

  public T getByUuid(String uuid) {
    IFilter filter = DefaultFiltersBuilder.getAndInstance()
      .andEqual(getUuidFieldName(), uuid)
      .build();
    List<T> ts = getMapper().search(filter, 0, 1, null);
    return ts != null && ts.size() > 0 ? ts.get(0) : null;
  }
}
