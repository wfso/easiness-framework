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
 * create by WFSO (仵士杰) at 2020/4/11 20:32
 */
public abstract class EqualsBackupHandler<T> extends AbstractBackupHandler<T> {
  protected EqualsBackupHandler() {
    super(EqualsBackupHandler.class);
  }

  @Override
  public boolean supports(Object object) {
    return clazz == object.getClass();
  }
}
