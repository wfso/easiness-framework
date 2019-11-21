/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus;

import cn.ibestcode.easiness.eventbus.dispatcher.BreadthDispatcher;
import cn.ibestcode.easiness.eventbus.dispatcher.Dispatcher;
import cn.ibestcode.easiness.eventbus.subscriber.Subscriber;
import cn.ibestcode.easiness.eventbus.subscriber.SubscriberResolver;
import cn.ibestcode.easiness.eventbus.subscriber.OmnipotentSubscriberResolver;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/19 19:56
 */
public class EasinessEventBus implements EventBus {

  // 使用线程安全的容器类
  private final ConcurrentMap<Class, CopyOnWriteArraySet<Subscriber>> listeners = new ConcurrentHashMap<>();

  private final SubscriberResolver subscriberResolver;

  private final Dispatcher dispatcher;

  public EasinessEventBus(Executor asyncExecutor, Dispatcher dispatcher) {
    this.subscriberResolver = new OmnipotentSubscriberResolver(asyncExecutor);
    this.dispatcher = dispatcher;
  }

  public EasinessEventBus(Executor asyncExecutor,
                          Dispatcher dispatcher,
                          Class<? extends Annotation> subscribeAnnotation,
                          Class<? extends Annotation> listenerAnnotation,
                          Class<? extends Annotation> synchronizedAnnotation,
                          Class<? extends Annotation> multithreadingAnnotation) {
    this.subscriberResolver = new OmnipotentSubscriberResolver(
      asyncExecutor,
      subscribeAnnotation,
      listenerAnnotation,
      synchronizedAnnotation,
      multithreadingAnnotation
    );
    this.dispatcher = dispatcher;
  }

  public EasinessEventBus(
    int pollSize,
    Dispatcher dispatcher,
    Class<? extends Annotation> subscribeAnnotation,
    Class<? extends Annotation> listenerAnnotation,
    Class<? extends Annotation> synchronizedAnnotation,
    Class<? extends Annotation> multithreadingAnnotation) {
    this(
      Executors.newScheduledThreadPool(pollSize),
      dispatcher,
      subscribeAnnotation,
      listenerAnnotation,
      synchronizedAnnotation,
      multithreadingAnnotation
    );
  }


  public EasinessEventBus(int pollSize, Dispatcher dispatcher) {
    this(Executors.newScheduledThreadPool(pollSize), dispatcher);
  }

  public EasinessEventBus(Dispatcher dispatcher) {
    this(10, dispatcher);
  }

  public EasinessEventBus(int pollSize) {
    this(pollSize, new BreadthDispatcher());
  }

  public EasinessEventBus() {
    this(10);
  }

  @Override
  public void register(Object listener) {
    List<Subscriber> subscriberList = subscriberResolver.getListeners(listener);
    Map<Class, List<Subscriber>> map = mergeToMap(subscriberList);

    for (Map.Entry<Class, List<Subscriber>> entry : map.entrySet()) {
      Class eventType = entry.getKey();
      List<Subscriber> list = entry.getValue();

      CopyOnWriteArraySet<Subscriber> subscriberSet = listeners.get(eventType);

      if (subscriberSet == null) {
        CopyOnWriteArraySet<Subscriber> newSet = new CopyOnWriteArraySet<>();
        CopyOnWriteArraySet<Subscriber> tmpSet = listeners.putIfAbsent(eventType, newSet);
        subscriberSet = tmpSet == null ? newSet : tmpSet;
      }

      subscriberSet.addAll(list);
    }

  }

  @Override
  public void unregister(Object listener) {
    List<Subscriber> subscriberList = subscriberResolver.getListeners(listener);
    Map<Class, List<Subscriber>> map = mergeToMap(subscriberList);

    for (Map.Entry<Class, List<Subscriber>> entry : map.entrySet()) {
      Class eventType = entry.getKey();
      List<Subscriber> list = entry.getValue();

      CopyOnWriteArraySet<Subscriber> subscriberSet = listeners.get(eventType);

      if (subscriberSet == null || !subscriberSet.removeAll(list)) {
        throw new IllegalArgumentException(
          "missing event subscriber for an annotated method. Is " + listener + " registered?");
      }

    }
  }

  @Override
  public void post(Object event) {
    List<Subscriber> subscribers = getListenersByEvent(event);
    if (subscribers != null && subscribers.size() > 0) {
      dispatcher.dispatch(event, subscribers);
    } else if (!(event instanceof FailedEvent)) {
      // 如果事件没有订阅者，且不是失败事件，则触发失败事件
      post(new FailedEvent(this, event));
    }
  }

  private Map<Class, List<Subscriber>> mergeToMap(List<Subscriber> subscribers) {
    final Map<Class, List<Subscriber>> map = new HashMap<>();
    for (Subscriber subscriber : subscribers) {
      if (!map.containsKey(subscriber.supports())) {
        map.put(subscriber.supports(), new ArrayList<>());
      }
      map.get(subscriber.supports()).add(subscriber);
    }
    return map;
  }

  private List<Subscriber> getListenersByEvent(Object event) {
    List<Subscriber> subscriberList = new ArrayList<>();
    for (Class eventType : getClassHierarchy(event.getClass())) {
      Set<Subscriber> subscriberSet = listeners.get(eventType);
      if (subscriberSet != null) {
        subscriberList.addAll(subscriberSet);
      }
    }
    return subscriberList;
  }

  // 获取类的承继结构
  private List<Class> getClassHierarchy(Class clazz) {
    List<Class> classes = new ArrayList<>();
    while (!Object.class.equals(clazz)) {
      classes.add(clazz);
      // move to the upper class in the hierarchy in search for more methods
      clazz = clazz.getSuperclass();
    }
    return classes;
  }

}
