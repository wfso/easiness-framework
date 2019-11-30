package cn.ibestcode.easiness.backup.entity.service;

import cn.ibestcode.easiness.backup.entity.annotation.BackupResult;
import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;

import javax.transaction.Transactional;
import java.util.List;

public abstract class UuidBaseJpaBackupService<T extends UuidBaseJpaModel> extends UuidBaseJpaService<T> {

  @Override
  @Transactional
  @BackupResult
  public T create(T entity) {
    return super.create(entity);
  }

  @Override
  @Transactional
  @BackupResult
  public T update(T entity) {
    return super.update(entity);
  }

  @Override
  @Transactional
  @BackupResult
  public List<T> create(Iterable<T> entities) {
    return super.create(entities);
  }

  @Override
  @Transactional
  @BackupResult
  public List<T> update(Iterable<T> entities) {
    return super.update(entities);
  }

  @Override
  @Transactional
  @BackupResult
  public T updateIgnoreNull(T entity) {
    return super.updateIgnoreNull(entity);
  }

  @Override
  @Transactional
  @BackupResult
  public T updateIgnoreEmpty(T entity) {
    return super.updateIgnoreEmpty(entity);
  }


  @Override
  @Transactional
  @BackupResult
  public List<T> updateIgnoreNull(Iterable<T> entities) {
    return super.updateIgnoreNull(entities);
  }

  @Override
  @Transactional
  @BackupResult
  public List<T> updateIgnoreEmpty(Iterable<T> entities) {
    return super.updateIgnoreEmpty(entities);
  }


  @Override
  @BackupResult
  @Transactional
  public Iterable<T> remove(Iterable<T> entities) {
    return super.remove(entities);
  }

  @Override
  @Transactional
  @BackupResult
  public Iterable<T> removeByIds(Iterable<Long> ids) {
    return super.removeByIds(ids);
  }

  @Override
  @Transactional
  @BackupResult
  public T removeById(Long id) {
    return super.removeById(id);
  }

  @Override
  @BackupResult
  @Transactional
  public T remove(T entity) {
    return super.remove(entity);
  }


  @Override
  @Transactional
  @BackupResult
  public T removeByUuid(String uuid) {
    return super.removeByUuid(uuid);
  }

  @Override
  @Transactional
  @BackupResult
  public T updateByUuid(T entity) {
    return super.updateByUuid(entity);
  }

  @Override
  @Transactional
  @BackupResult
  public T updateByUuidIgnoreNull(T entity) {
    return super.updateByUuidIgnoreNull(entity);
  }

  @Override
  @Transactional
  @BackupResult
  public T updateByUuidIgnoreEmpty(T entity) {
    return super.updateByUuidIgnoreEmpty(entity);
  }

}
