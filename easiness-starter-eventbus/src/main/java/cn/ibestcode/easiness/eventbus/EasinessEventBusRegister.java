/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/25 19:56
 */
@Component
@Order(value=1)
public class EasinessEventBusRegister implements CommandLineRunner {

  @Autowired
  private EventBus eventBus;

  @EventListener
  private List<Object> Listeners;

  @Override
  public void run(String... args) throws Exception {
    for (Object listener : Listeners) {
      eventBus.register(listener);
    }
    Listeners.clear();
  }
}
