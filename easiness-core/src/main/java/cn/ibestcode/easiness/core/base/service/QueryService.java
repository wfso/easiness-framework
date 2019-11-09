package cn.ibestcode.easiness.core.base.service;

import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QueryService<T, ID> {

  T getById(ID id);

  List<T> getByIds(Iterable<ID> ids);

  long countAll();

  long count(List<? extends IFilter> filters);

  long count(IFilter filter);

  default long count(FilterGenerator generator) {
    return count(generator.generateFilter());
  }

  List<T> getAll();

  Page<T> getPage(Pageable pageable);

  Page<T> getPage(List<? extends IFilter> filters, Pageable pageable);

  List<T> getList(List<? extends IFilter> filters);

  List<T> getList(List<? extends IFilter> filters, Sort sort);

  Page<T> getPage(IFilter filter, Pageable pageable);

  List<T> getList(IFilter filter);

  List<T> getList(IFilter filter, Sort sort);

  default Page<T> getPage(FilterGenerator generator, Pageable pageable) {
    return getPage(generator.generateFilter(), pageable);
  }

  default List<T> getList(FilterGenerator generator) {
    return getList(generator.generateFilter());
  }

  default List<T> getList(FilterGenerator generator, Sort sort) {
    return getList(generator.generateFilter(), sort);
  }
}
