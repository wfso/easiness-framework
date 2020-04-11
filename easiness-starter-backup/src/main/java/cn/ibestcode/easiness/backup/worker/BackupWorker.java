package cn.ibestcode.easiness.backup.worker;

import cn.ibestcode.easiness.backup.entity.BackupMetadata;

public interface BackupWorker {
  void backup(Object object, BackupMetadata metadata);
}
