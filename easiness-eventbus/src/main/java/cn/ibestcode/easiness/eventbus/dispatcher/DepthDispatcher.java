/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus.dispatcher;

import cn.ibestcode.easiness.eventbus.subscriber.Subscriber;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/18 19:55
 */
public class DepthDispatcher implements Dispatcher {

  @Override
  public void dispatch(Object event, List<Subscriber> subscribers) {
    for (Subscriber subscriber : subscribers) {
      subscriber.handle(event);
    }
  }
}
