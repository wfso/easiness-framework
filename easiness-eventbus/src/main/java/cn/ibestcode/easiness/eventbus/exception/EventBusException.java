/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus.exception;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/18 19:55
 */
public class EventBusException extends RuntimeException{
  public EventBusException() {
  }

  public EventBusException(String message) {
    super(message);
  }

  public EventBusException(String message, Throwable cause) {
    super(message, cause);
  }

  public EventBusException(Throwable cause) {
    super(cause);
  }
}
