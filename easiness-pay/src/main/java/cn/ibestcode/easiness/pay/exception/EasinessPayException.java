/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.exception;


import cn.ibestcode.easiness.core.exception.EasinessException;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 20:19
 */
public class EasinessPayException extends EasinessException {
  public EasinessPayException(String message) {
    super(message);
  }

  public EasinessPayException(String message, String... params) {
    super(message, params);
  }

  public EasinessPayException(String message, Throwable cause) {
    super(message, cause);
  }

  public EasinessPayException(String message, Throwable cause, String... params) {
    super(message, cause, params);
  }
}
