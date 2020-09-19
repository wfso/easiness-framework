package cn.ibestcode.easiness.core.base.service;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import cn.ibestcode.easiness.core.base.repository.UuidBaseJpaRepository;
import cn.ibestcode.easiness.utils.RandomUtil;
import cn.ibestcode.easiness.utils.SpringBeanUtilsExt;
import org.apache.commons.lang3.StringUtils;

import org.springframework.transaction.annotation.Transactional;

public abstract class UuidBaseJpaService<T extends UuidBaseJpaModel> extends BaseJpaService<T> implements UuidService<T> {
  protected abstract UuidBaseJpaRepository<T> getRepository();

  @Override
  public T getByUuid(String uuid) {
    return getRepository().findByUuid(uuid);
  }

  @Override
  @Transactional
  public T readByUuid(String uuid) {
    return getRepository().readByUuid(uuid);
  }

  @Override
  @Transactional
  public T removeByUuid(String uuid) {
    T entity = getByUuid(uuid);
    getRepository().deleteByUuid(uuid);
    return entity;
  }

  @Override
  @Transactional
  public T updateByUuid(T entity) {
    T t = getByUuid(entity.getUuid());
    if (t != null && t.getId() != null && t.getId() > 0) {
      entity.setId(t.getId());
      update(entity);
    }
    return entity;
  }

  @Override
  @Transactional
  public T updateByUuidIgnoreNull(T entity) {
    T target = genFreeEntityFromDbByUuid(entity);
    SpringBeanUtilsExt.copyPropertiesIgnoreNull(entity, target);
    return update(target);
  }

  @Override
  @Transactional
  public T updateByUuidIgnoreEmpty(T entity) {
    T target = genFreeEntityFromDbByUuid(entity);
    SpringBeanUtilsExt.copyPropertiesIgnoreEmpty(entity, target);
    return update(target);
  }

  @Override
  protected void initCreateDomain(T entity) {
    super.initCreateDomain(entity);
    if (StringUtils.isBlank(entity.getUuid())) {
      entity.setUuid(RandomUtil.generateUnseparatedUuid());
    }
  }

  protected T genFreeEntityFromDbByUuid(T entity) {
    T target = newEntity(entity);
    T source = getByUuid(entity.getUuid());
    SpringBeanUtilsExt.copyProperties(source, target);
    return target;
  }

}
