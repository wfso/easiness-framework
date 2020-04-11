/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.backup.handler;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/11 20:31
 */
public abstract class InstanceOfBackupHandler<T> extends AbstractBackupHandler<T> {
  public InstanceOfBackupHandler() {
    super(InstanceOfBackupHandler.class);
  }

  @Override
  public boolean supports(Object object) {
    return clazz.isInstance(object);
  }
}
