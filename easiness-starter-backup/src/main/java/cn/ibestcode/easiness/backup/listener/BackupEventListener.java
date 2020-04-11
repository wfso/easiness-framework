/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.backup.listener;

import cn.ibestcode.easiness.backup.domain.BackupEvent;
import cn.ibestcode.easiness.backup.worker.BackupWorker;
import cn.ibestcode.easiness.eventbus.EventListener;
import cn.ibestcode.easiness.eventbus.annotation.Multithreading;
import cn.ibestcode.easiness.eventbus.annotation.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/11 22:54
 */
@EventListener
@ConditionalOnProperty(name = "easiness.backup.async", havingValue = "true", matchIfMissing = true)
public class BackupEventListener {

  @Autowired
  private List<BackupWorker> workers;

  @Subscribe
  @Multithreading
  public void backupEventListener(BackupEvent backupEvent) {
    for (BackupWorker worker : workers) {
      worker.backup(backupEvent.getObject(), backupEvent.getMetadata());
    }
  }
}
