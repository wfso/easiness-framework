/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus.subscriber;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/18 19:55
 */
public final class SynchronizedOmnipotentSubscriber extends OmnipotentSubscriber {

  public SynchronizedOmnipotentSubscriber(Object target, Method method, Executor executor) {
    super(target, method, executor);
  }

  public SynchronizedOmnipotentSubscriber(Object target, Method method) {
    super(target, method);
  }

  @Override
  public void handle(Object event) {
    synchronized (this) {
      super.handle(event);
    }
  }
}
