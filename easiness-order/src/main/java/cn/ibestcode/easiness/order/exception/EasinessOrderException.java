/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.exception;

import cn.ibestcode.easiness.core.exception.EasinessException;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
public class EasinessOrderException extends EasinessException {
  public EasinessOrderException(String message) {
    super(message);
  }

  public EasinessOrderException(String message, String... params) {
    super(message, params);
  }

  public EasinessOrderException(String message, Throwable cause) {
    super(message, cause);
  }

  public EasinessOrderException(String message, Throwable cause, String... params) {
    super(message, cause, params);
  }
}
