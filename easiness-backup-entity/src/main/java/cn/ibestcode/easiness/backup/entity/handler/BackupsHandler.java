package cn.ibestcode.easiness.backup.entity.handler;

import cn.ibestcode.easiness.backup.entity.BackupMetadata;

public interface BackupsHandler<T> {
  boolean supports(T object);

  void backup(T entity, BackupMetadata metadata);
}
