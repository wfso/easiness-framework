package cn.ibestcode.easiness.core.base.service;

import cn.ibestcode.easiness.core.base.mapper.Mapper;
import cn.ibestcode.easiness.core.query.builder.DefaultFiltersBuilder;
import cn.ibestcode.easiness.core.query.filter.DefaultFilter;
import cn.ibestcode.easiness.core.query.filter.FilterType;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMyBatisService<T, ID> implements QueryService<T, ID> {

  protected abstract Mapper<T> getMapper();

  protected abstract String getIdFieldName();

  protected String formatIdToString(ID id) {
    return id.toString();
  }

  @Override
  public T getById(ID id) {
    IFilter filter = DefaultFiltersBuilder.getAndInstance()
      .andEqual(getIdFieldName(), formatIdToString(id))
      .build();
    List<T> ts = getMapper().search(filter, 0, 1, null);
    return ts != null && ts.size() > 0 ? ts.get(0) : null;
  }

  @Override
  public List<T> getByIds(Iterable<ID> ids) {
    List<String> strs = new ArrayList<>();
    for (ID id : ids) {
      strs.add(formatIdToString(id));
    }
    IFilter filter = DefaultFiltersBuilder.getAndInstance()
      .andIn(getIdFieldName(), strs)
      .build();
    return getMapper().search(filter, 0, strs.size(), null);
  }

  @Override
  public long countAll() {
    return getMapper().count(null);
  }

  @Override
  public long count(List<? extends IFilter> filters) {
    return count(new DefaultFilter(FilterType.and, filters));
  }

  @Override
  public long count(IFilter filter) {
    return getMapper().count(filter);
  }

  @Override
  public List<T> getAll() {
    return getMapper().search(null, 0, countAll(), null);
  }

  @Override
  public Page<T> getPage(Pageable pageable) {
    long total = countAll();
    List<T> ts = getMapper().search(null, pageable.getOffset(), pageable.getPageSize(), genOrder(pageable.getSort()));
    return new PageImpl<>(ts, pageable, total);
  }

  @Override
  public Page<T> getPage(List<? extends IFilter> filters, Pageable pageable) {
    return getPage(new DefaultFilter(FilterType.and, filters), pageable);
  }

  @Override
  public List<T> getList(List<? extends IFilter> filters) {
    return getList(new DefaultFilter(FilterType.and, filters));
  }

  @Override
  public List<T> getList(List<? extends IFilter> filters, Sort sort) {
    return getList(new DefaultFilter(FilterType.and, filters), sort);
  }

  @Override
  public Page<T> getPage(IFilter filter, Pageable pageable) {
    long total = count(filter);
    List<T> ts = getMapper().search(filter, pageable.getOffset(), pageable.getPageSize(), genOrder(pageable.getSort()));
    return new PageImpl<>(ts, pageable, total);
  }

  @Override
  public List<T> getList(IFilter filter) {
    long total = count(filter);
    return getMapper().search(filter, 0, total, null);
  }

  @Override
  public List<T> getList(IFilter filter, Sort sort) {
    long total = count(filter);
    return getMapper().search(filter, 0, total, genOrder(sort));
  }

  protected String genOrder(Sort sort) {
    StringBuilder orderBy = new StringBuilder();
    for (Sort.Order order : sort) {
      orderBy.append(order.getProperty())
        .append(" ")
        .append(order.getDirection().name())
        .append(",");
    }
    if (orderBy.length() > 0) {
      return orderBy.substring(0, orderBy.length() - 1);
    } else {
      return null;
    }
  }
}
