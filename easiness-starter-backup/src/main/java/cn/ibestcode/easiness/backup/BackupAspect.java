/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.backup;

import cn.ibestcode.easiness.backup.domain.BackupEvent;
import cn.ibestcode.easiness.backup.entity.BackupMetadata;
import cn.ibestcode.easiness.backup.entity.annotation.BackupArgs;
import cn.ibestcode.easiness.backup.entity.annotation.BackupResult;
import cn.ibestcode.easiness.backup.properties.EasinessBackupProperties;
import cn.ibestcode.easiness.backup.worker.BackupWorker;
import cn.ibestcode.easiness.eventbus.EventBus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/11 16:57
 */
@Aspect
@Component
public class BackupAspect {

  @Autowired
  private List<BackupWorker> workers;

  @Autowired
  private EventBus eventBus;

  @Autowired
  private EasinessBackupProperties properties;

  @Before("@annotation(backupArgs)")
  public void backupArgs(JoinPoint point, BackupArgs backupArgs) {
    BackupMetadata metadata = new BackupMetadata(backupArgs, point);
    for (Object object : point.getArgs()) {
      if (object instanceof Iterable<?>) {
        backup((Iterable<?>) object, metadata);
      } else {
        backup(object, metadata);
      }
    }
  }

  @AfterReturning(returning = "result", pointcut = "@annotation(backupResult)")
  public void backupResult(JoinPoint point, BackupResult backupResult, Object result) {
    BackupMetadata metadata = new BackupMetadata(backupResult, point);
    if (result instanceof Iterable<?>) {
      backup((Iterable<?>) result, metadata);
    } else {
      backup(result, metadata);
    }
  }

  private void backup(Iterable<?> iterable, BackupMetadata metadata) {
    for (Object object : iterable) {
      if (object instanceof Iterable<?>) {
        backup((Iterable<?>) object, metadata);
      } else {
        backup(object, metadata);
      }
    }
  }

  private void backup(Object object, BackupMetadata metadata) {
    if (properties.isAsync()) {
      eventBus.post(new BackupEvent(metadata, object));
    } else {
      for (BackupWorker worker : workers) {
        worker.backup(object, metadata);
      }
    }
  }

}
