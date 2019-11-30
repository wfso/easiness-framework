package cn.ibestcode.easiness.backup.entity;


import cn.ibestcode.easiness.backup.entity.annotation.BackupArgs;
import cn.ibestcode.easiness.backup.entity.annotation.BackupResult;
import org.aspectj.lang.JoinPoint;

import java.lang.annotation.Annotation;

public class BackupMetadata {

  private Annotation annotation;

  private JoinPoint joinPoint;

  private String backupType;

  private String targetMethodName;

  public JoinPoint getJoinPoint() {
    return joinPoint;
  }

  public String getBackupType() {
    if (backupType == null) {
      parseAnnotated();
    }
    return backupType;
  }


  public String getTargetMethodName() {
    if (targetMethodName == null) {
      targetMethodName = joinPoint.getSignature().getName();
    }
    return targetMethodName;
  }

  public BackupMetadata(Annotation annotation, JoinPoint joinPoint) {
    this.annotation = annotation;
    this.joinPoint = joinPoint;
  }

  protected void parseAnnotated() {
    if (annotation instanceof BackupArgs) {
      BackupArgs backupArgs = (BackupArgs) annotation;
      backupType = backupArgs.type();
      return;
    }
    if (annotation instanceof BackupResult) {
      BackupResult backupResult = (BackupResult) annotation;
      backupType = backupResult.type();
      return;
    }
  }
}
