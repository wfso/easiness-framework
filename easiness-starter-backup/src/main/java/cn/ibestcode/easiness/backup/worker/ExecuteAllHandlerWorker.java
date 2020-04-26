/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.backup.worker;

import cn.ibestcode.easiness.backup.entity.BackupMetadata;
import cn.ibestcode.easiness.backup.entity.handler.BackupHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/11 17:10
 */
@Component
@ConditionalOnProperty(value = "easiness.backup.handler.execute.all", matchIfMissing = true)
public class ExecuteAllHandlerWorker implements BackupWorker {

  @Autowired
  private List<BackupHandler> handlers;

  private Map<Class, List<BackupHandler>> handlersMap = new HashMap<>();

  @Override
  public void backup(Object object, BackupMetadata metadata) {
    Class clazz = object.getClass();
    if (!handlersMap.containsKey(clazz)) {
      List<BackupHandler> handlerList = new ArrayList<>();
      for (BackupHandler handler : handlers) {
        if (handler.supports(object)) {
          handlerList.add(handler);
        }
      }
      handlersMap.put(clazz, handlerList);
    }
    List<BackupHandler> handlerList = handlersMap.get(clazz);
    for (BackupHandler handler : handlerList) {
      handler.backup(object, metadata);
    }
  }
}
