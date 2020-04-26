/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.backup.worker;

import cn.ibestcode.easiness.backup.entity.BackupMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/11 17:08
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "easiness.backup.toLog", matchIfMissing = true)
public class BackupToLogWorker implements BackupWorker {
  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void backup(Object object, BackupMetadata metadata) {
    StringBuilder sb = new StringBuilder();
    sb.append("BACKUP-TO-LOGGER [")
      .append(metadata.getBackupType())
      .append("] [")
      .append(metadata.getJoinPoint().getSignature().toString())
      .append("] [")
      .append(metadata.getJoinPoint().getTarget().getClass().getName())
      .append(".")
      .append(metadata.getJoinPoint().getSignature().getName())
      .append("] [")
      .append(object.getClass().getName())
      .append("] : ");
    try {
      sb.append(objectMapper.writeValueAsString(object));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    }
    log.info(sb.toString());
  }
}
