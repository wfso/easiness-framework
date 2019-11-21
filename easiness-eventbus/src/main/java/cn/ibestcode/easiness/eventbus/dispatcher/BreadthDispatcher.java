/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus.dispatcher;

import cn.ibestcode.easiness.eventbus.subscriber.Subscriber;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/18 19:55
 */
public class BreadthDispatcher implements Dispatcher {

  /**
   * 每个线程一个事件队列，用于支持通过 EventBus.post(event) 从多线程同时触发事件；
   * 但每次调用 EventBus.post(event) 所触发的全部事件监听器，都会在调用
   * EventBus.post(event) 的当前线程中执行（不会开新的线程执行事件监听器）；
   */
  private final ThreadLocal<Queue<EventWithSubscriber>> queue =
    new ThreadLocal<Queue<EventWithSubscriber>>() {
      @Override
      protected Queue<EventWithSubscriber> initialValue() {
        return new ArrayDeque<>();
      }
    };

  /**
   * 每个线程一个调度器状态, 用于避免单个线程内重入事件调度。
   */
  private final ThreadLocal<Boolean> dispatching =
    new ThreadLocal<Boolean>() {
      @Override
      protected Boolean initialValue() {
        return false;
      }
    };

  @Override
  public void dispatch(Object event, List<Subscriber> subscribers) {
    if (event == null) {
      throw new NullPointerException("the event can't be null");
    }
    if (subscribers == null) {
      throw new NullPointerException("the subscribers can't be null");
    }

    Queue<EventWithSubscriber> queueForThread = queue.get();
    for (Subscriber subscriber : subscribers) {
      queueForThread.offer(new EventWithSubscriber(event, subscriber));
    }

    if (!dispatching.get()) {
      dispatching.set(true);
      try {
        EventWithSubscriber eventWithSubscriber;
        while ((eventWithSubscriber = queueForThread.poll()) != null) {
          eventWithSubscriber.subscriber.handle(eventWithSubscriber.event);
        }
      } finally {
        dispatching.remove();
        queue.remove();
      }
    }
  }

  private static class EventWithSubscriber {
    private final Object event;
    private final Subscriber subscriber;

    public EventWithSubscriber(Object event, Subscriber subscriber) {
      this.event = event;
      this.subscriber = subscriber;
    }
  }
}
