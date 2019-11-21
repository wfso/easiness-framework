/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus.subscriber;

import cn.ibestcode.easiness.eventbus.annotation.Multithreading;
import cn.ibestcode.easiness.eventbus.annotation.Subscribe;
import cn.ibestcode.easiness.eventbus.annotation.TestEventListener;
import cn.ibestcode.easiness.eventbus.event.LogEvent;
import cn.ibestcode.easiness.eventbus.event.ExceptionEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/19 19:18
 */
@Slf4j
@TestEventListener
public class MultithreadingListener {

  @Subscribe
  @Multithreading
  public void logEventProcessor(LogEvent event) {
    log.info(this.getClass().getName() + " 处理了 " + event);
  }


  @Subscribe
  @Multithreading
  public void logEventProcessor2(LogEvent event) {
    log.info(this.getClass().getName() + " 处理了 " + event);
  }


  @Subscribe
  @Multithreading
  public void logEventProcessor3(LogEvent event) {
    log.info(this.getClass().getName() + " 处理了 " + event);
  }


  @Subscribe
  @Multithreading
  public void exceptionEventProcessor(ExceptionEvent event) {
    throw new RuntimeException("抛个异常试试");
  }

  @Subscribe
  @Multithreading
  public void exceptionEventProcessor2(ExceptionEvent event) {
    throw new RuntimeException("抛个异常试试");
  }

  @Subscribe
  @Multithreading
  public void exceptionEventProcessor3(ExceptionEvent event) {
    throw new RuntimeException("抛个异常试试");
  }
}
