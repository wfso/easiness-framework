/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.backup;

import cn.ibestcode.easiness.backup.worker.BackupToLogWorker;
import cn.ibestcode.easiness.backup.worker.ExecuteAllHandlerWorker;
import cn.ibestcode.easiness.backup.worker.ExecuteSingleHandlerWorker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/11 15:50
 */
public class BackupConfiguration {

  @Bean
  @ConditionalOnProperty(value = "easiness.backup.toLog", matchIfMissing = true)
  public BackupToLogWorker backupToLogWorker() {
    return new BackupToLogWorker();
  }

  @Bean
  @ConditionalOnProperty(value = "easiness.backup.handler.execute.all", matchIfMissing = true)
  public ExecuteAllHandlerWorker executeAllHandlerWorker() {
    return new ExecuteAllHandlerWorker();
  }

  @Bean
  @ConditionalOnProperty(value = "easiness.backup.handler.execute.all", havingValue = "false")
  public ExecuteSingleHandlerWorker executeSingleHandlerWorker() {
    return new ExecuteSingleHandlerWorker();
  }
}
