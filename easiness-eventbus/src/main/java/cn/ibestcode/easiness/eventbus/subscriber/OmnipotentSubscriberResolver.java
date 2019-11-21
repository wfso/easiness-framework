/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus.subscriber;

import cn.ibestcode.easiness.eventbus.annotation.Multithreading;
import cn.ibestcode.easiness.eventbus.annotation.Synchronized;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/19 19:15
 */
public class OmnipotentSubscriberResolver extends AbstractSubscriberResolver {
  private final Class<? extends Annotation> synchronizedAnnotation;
  private final Class<? extends Annotation> multithreadingAnnotation;
  private final Executor asyncExecutor;

  public OmnipotentSubscriberResolver(Executor asyncExecutor) {
    this.asyncExecutor = asyncExecutor;
    this.synchronizedAnnotation = Synchronized.class;
    this.multithreadingAnnotation = Multithreading.class;
  }

  public OmnipotentSubscriberResolver(Executor asyncExecutor,
                                      Class<? extends Annotation> subscribeAnnotation,
                                      Class<? extends Annotation> listenerAnnotation,
                                      Class<? extends Annotation> synchronizedAnnotation,
                                      Class<? extends Annotation> multithreadingAnnotation) {
    super(subscribeAnnotation, listenerAnnotation);
    this.asyncExecutor = asyncExecutor;
    this.synchronizedAnnotation = synchronizedAnnotation;
    this.multithreadingAnnotation = multithreadingAnnotation;
  }

  @Override
  protected Subscriber generateListener(Object instance, Method method) {
    if (isAnnotationPresent(method, multithreadingAnnotation)) {
      return isAnnotationPresent(method, synchronizedAnnotation) ?
        new SynchronizedOmnipotentSubscriber(instance, method, asyncExecutor)
        : new OmnipotentSubscriber(instance, method, asyncExecutor);
    } else {
      return isAnnotationPresent(method, synchronizedAnnotation) ?
        new SynchronizedOmnipotentSubscriber(instance, method)
        : new OmnipotentSubscriber(instance, method);
    }
  }
}
