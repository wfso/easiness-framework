/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus.event;

import lombok.Getter;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/19 19:19
 */
public class ExceptionEvent {

  @Getter
  private String eventName;

  public ExceptionEvent(String eventName) {
    this.eventName = eventName;
  }

  public String toString() {
    return this.getClass().getName() + "(" + getEventName() + ")";
  }
}
