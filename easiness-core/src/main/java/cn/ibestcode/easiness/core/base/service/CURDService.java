package cn.ibestcode.easiness.core.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface CURDService<T, ID> {

  long countAll();

  List<T> getAll();

  Page<T> getPage(Pageable pageable);

  // 无锁读
  T getById(ID id);

  // 共享锁的读
  T readById(ID id);

  List<T> getByIds(Iterable<ID> ids);

  T create(T entity);

  T update(T entity);

  List<T> create(Iterable<T> entities);

  List<T> update(Iterable<T> entities);

  T updateIgnoreNull(T entity);

  T updateIgnoreEmpty(T entity);

  @Transactional
  default List<T> updateIgnoreNull(Iterable<T> entities) {
    List<T> list = new ArrayList<>();
    for (T entity : entities) {
      list.add(updateIgnoreNull(entity));
    }
    return list;
  }

  @Transactional
  default List<T> updateIgnoreEmpty(Iterable<T> entities) {
    List<T> list = new ArrayList<>();
    for (T entity : entities) {
      list.add(updateIgnoreEmpty(entity));
    }
    return list;
  }

  T remove(T entity);

  Iterable<T> remove(Iterable<T> entities);

  T removeById(ID id);

  @Transactional
  default Iterable<T> removeByIds(Iterable<ID> ids) {
    List<T> list = getByIds(ids);
    return remove(list);
  }
}
