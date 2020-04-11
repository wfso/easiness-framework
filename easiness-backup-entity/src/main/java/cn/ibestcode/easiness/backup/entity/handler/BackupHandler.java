package cn.ibestcode.easiness.backup.entity.handler;

import cn.ibestcode.easiness.backup.entity.BackupMetadata;

public interface BackupHandler<T> {
  boolean supports(Object object);

  void backup(T entity, BackupMetadata metadata);
}
