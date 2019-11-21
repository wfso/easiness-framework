/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus;

import cn.ibestcode.easiness.eventbus.event.LogEvent;
import cn.ibestcode.easiness.eventbus.event.ExceptionEvent;
import cn.ibestcode.easiness.eventbus.subscriber.MultithreadingListener;
import cn.ibestcode.easiness.eventbus.subscriber.TransactionListener;
import cn.ibestcode.easiness.eventbus.subscriber.UnregisterListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/19 19:09
 */
@RunWith(JUnit4.class)
@Slf4j
public class EasinessEventBusTest {

  private EventBus eventBus = new EasinessEventBus();

  @Before
  public void before() {
    eventBus.register(new MultithreadingListener());
    eventBus.register(new TransactionListener());
  }

  @Test
  public void logEventTest() {
    eventBus.post(new LogEvent("test"));
    eventBus.post(new LogEvent("test"));
    eventBus.post(new LogEvent("test"));
    eventBus.post(new LogEvent("test"));
    eventBus.post(new LogEvent("test"));
    eventBus.post(new LogEvent("test"));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void exceptionEventTest() {
    try {
      eventBus.post(new ExceptionEvent("test"));
    } catch (Throwable e) {
      log.warn(e.getMessage(), e);
    }
    try {
      eventBus.post(new ExceptionEvent("test"));
    } catch (Throwable e) {
      log.warn(e.getMessage(), e);
    }
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      log.warn(e.getMessage(), e);
    }
  }

  @Test
  public void unregisterTest() {
    Object object = new UnregisterListener();
    eventBus.register(object);
    eventBus.post(new LogEvent("哈哈"));
    log.warn("=======================================================");
    eventBus.unregister(object);
    eventBus.post(new LogEvent("哈哈"));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      log.warn(e.getMessage(), e);
    }
  }
}
