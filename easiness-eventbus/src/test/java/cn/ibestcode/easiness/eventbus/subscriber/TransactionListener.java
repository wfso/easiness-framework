/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus.subscriber;

import cn.ibestcode.easiness.eventbus.annotation.Subscribe;
import cn.ibestcode.easiness.eventbus.annotation.TestEventListener;
import cn.ibestcode.easiness.eventbus.event.ExceptionEvent;
import cn.ibestcode.easiness.eventbus.event.LogEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/19 19:18
 */
@Slf4j
@TestEventListener
public class TransactionListener {

  @Subscribe
  public void logEventProcessor(LogEvent event) {
    log.info(this.getClass().getName() + " 处理了 " + event);
  }


  @Subscribe
  public void exceptionEventProcessor(ExceptionEvent event) {
    throw new RuntimeException("抛个异常试试");
  }
}
