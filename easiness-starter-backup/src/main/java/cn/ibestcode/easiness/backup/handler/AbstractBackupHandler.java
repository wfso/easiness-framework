/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.backup.handler;

import cn.ibestcode.easiness.backup.entity.handler.BackupHandler;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/11 20:34
 */
public abstract class AbstractBackupHandler<T> implements BackupHandler<T> {

  protected final Class clazz;

  protected AbstractBackupHandler(Class parentClass) {
    Class<?> abstractBackupHandlerSubclass = findImmediateSubclass(getClass(), parentClass);
    Type type = abstractBackupHandlerSubclass.getGenericSuperclass();
    Assert.isInstanceOf(ParameterizedType.class, type, "Type must be a parameterized type");
    ParameterizedType parameterizedType = (ParameterizedType) type;
    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    Assert.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");
    type = actualTypeArguments[0];
    Assert.isInstanceOf(Class.class, type);
    this.clazz = (Class) type;
  }

  protected static Class<?> findImmediateSubclass(Class<?> child, Class<?> parentClass) {
    Class<?> parent = child.getSuperclass();
    if (Object.class == parent) {
      throw new IllegalStateException("Expected AbstractBackupHandler superclass");
    } else if (parentClass == parent) {
      return child;
    } else {
      return findImmediateSubclass(parent, parentClass);
    }
  }
}
