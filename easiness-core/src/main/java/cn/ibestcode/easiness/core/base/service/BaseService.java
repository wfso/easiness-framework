package cn.ibestcode.easiness.core.base.service;

import cn.ibestcode.easiness.core.base.model.Model;
import cn.ibestcode.easiness.core.base.repository.Repository;
import cn.ibestcode.easiness.core.exception.EasinessException;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import cn.ibestcode.easiness.core.query.specification.AbstractSpecification;
import cn.ibestcode.easiness.utils.SpringBeanUtilsExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class BaseService<T extends Model<ID>, ID> implements Service<T, ID> {
  protected abstract Repository<T, ID> getRepository();

  protected ID generateId() {
    return null;
  }

  @Override
  @Transactional
  public T create(T entity) {
    initCreateDomain(entity);
    getRepository().save(entity);
    return entity;
  }

  @Override
  @Transactional
  public T update(T entity) {
    initUpdateDomain(entity);
    getRepository().save(entity);
    return entity;
  }

  @Override
  @Transactional
  public List<T> create(Iterable<T> entities) {
    for (T t : entities) {
      initCreateDomain(t);
    }
    return getRepository().saveAll(entities);
  }

  @Override
  @Transactional
  public List<T> update(Iterable<T> entities) {
    for (T t : entities) {
      initUpdateDomain(t);
    }
    return getRepository().saveAll(entities);
  }

  @Override
  @Transactional
  public T updateIgnoreNull(T entity) {
    T target = genFreeEntityFromDbById(entity);
    SpringBeanUtilsExt.copyPropertiesIgnoreNull(entity, target);
    return update(target);
  }

  @Override
  @Transactional
  public T updateIgnoreEmpty(T entity) {
    T target = genFreeEntityFromDbById(entity);
    SpringBeanUtilsExt.copyPropertiesIgnoreEmpty(entity, target);
    return update(target);
  }

  @Override
  @Transactional
  public T removeById(ID id) {
    T entity = getById(id);
    getRepository().deleteById(id);
    return entity;
  }

  @Override
  @Transactional
  public Iterable<T> remove(Iterable<T> entities) {
    getRepository().deleteInBatch(entities);
    return entities;
  }

  @Override
  @Transactional
  public T remove(T entity) {
    getRepository().delete(entity);
    return entity;
  }

  @Override
  public long countAll() {
    return getRepository().count();
  }

  @Override
  public long count(List<? extends IFilter> filters) {
    return getRepository().count(getSpecification(filters));
  }

  @Override
  public long count(IFilter filter) {
    return getRepository().count(getSpecification(filter));
  }

  @Override
  public T getById(ID id) {
    return getRepository().findById(id).orElse(null);
  }

  @Override
  public List<T> getByIds(Iterable<ID> ids) {
    return getRepository().findAllById(ids);
  }

  @Override
  public List<T> getAll() {
    return getRepository().findAll();
  }

  @Override
  public Page<T> getPage(Pageable pageable) {
    return getRepository().findAll(pageable);
  }

  @Override
  public Page<T> getPage(List<? extends IFilter> filters, Pageable pageable) {
    return getRepository().findAll(getSpecification(filters), pageable);
  }

  @Override
  public List<T> getList(List<? extends IFilter> filters) {
    return getRepository().findAll(getSpecification(filters));
  }

  @Override
  public List<T> getList(List<? extends IFilter> filters, Sort sort) {
    return getRepository().findAll(getSpecification(filters), sort);
  }

  @Override
  public Page<T> getPage(IFilter filter, Pageable pageable) {
    return getRepository().findAll(getSpecification(filter), pageable);
  }

  @Override
  public List<T> getList(IFilter filter) {
    return getRepository().findAll(getSpecification(filter));
  }

  @Override
  public List<T> getList(IFilter filter, Sort sort) {
    return getRepository().findAll(getSpecification(filter), sort);
  }

  protected void initCreateDomain(T entity) {
    initCommonDomain(entity);
    long time = System.currentTimeMillis();
    entity.setCreatedAt(time);
    entity.setId(generateId());
  }

  protected void initUpdateDomain(T entity) {
    initCommonDomain(entity);
  }

  protected void initCommonDomain(T entity) {
    long time = System.currentTimeMillis();
    entity.setUpdatedAt(time);
  }

  public static <T> Specification<T> getSpecification(IFilter filter) {
    if (filter == null || filter.getList().size() == 0) {
      return null;
    }
    return new AbstractSpecification<T>() {
      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return generatePredicate(filter, root, criteriaBuilder);
      }
    };
  }

  public static <T> Specification<T> getSpecification(List<? extends IFilter> filters) {
    return getSpecification(filters, true);
  }

  public static <T> Specification<T> getSpecification(List<? extends IFilter> filters, boolean and) {
    if (filters == null || filters.size() == 0) {
      return null;
    }
    return new AbstractSpecification<T>() {
      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return generatePredicate(filters, root, criteriaBuilder, and);
      }
    };
  }

  protected T genFreeEntityFromDbById(T entity) {
    T target = newEntity(entity);
    T source = getById(entity.getId());
    SpringBeanUtilsExt.copyProperties(source, target);
    return target;
  }

  protected T newEntity(T entity) {
    try {
      Object object = entity.getClass().newInstance();
      if (entity.getClass().isInstance(object)) {
        entity = (T) object;
      }
    } catch (InstantiationException | IllegalAccessException e) {
      log.warn(e.getMessage(), e);
      throw new EasinessException("GenerateEntityException", e);
    }
    return entity;
  }

}
