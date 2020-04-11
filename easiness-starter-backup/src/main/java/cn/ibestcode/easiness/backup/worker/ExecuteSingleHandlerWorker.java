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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/11 17:10
 */
public class ExecuteSingleHandlerWorker implements BackupWorker {
  @Autowired
  private List<BackupHandler> handlers;

  private Map<Class, BackupHandler> handlerMap = new HashMap<>();

  @Override
  public void backup(Object object, BackupMetadata metadata) {
    Class clazz = object.getClass();
    if (!handlerMap.containsKey(clazz)) {
      for (BackupHandler handler : handlers) {
        if (handler.supports(object)) {
          handlerMap.put(clazz, handler);
          break;
        }
      }
      if (!handlerMap.containsKey(clazz)) {
        handlerMap.put(clazz, null);
      }
    }
    BackupHandler handler = handlerMap.get(clazz);
    if (handler != null) {
      handler.backup(object, metadata);
    }
  }
}
