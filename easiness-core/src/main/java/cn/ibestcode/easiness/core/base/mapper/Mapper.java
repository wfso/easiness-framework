package cn.ibestcode.easiness.core.base.mapper;

import cn.ibestcode.easiness.core.query.filter.IFilter;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface Mapper<T> {

  long count(@Nullable @Param("filter") IFilter filter);

  List<T> search(
    @Nullable @Param("filter") IFilter filter,
    @Param("offset") long offset,
    @Param("size") long size,
    @Nullable @Param("orderBy") String orderBy
  );

}
