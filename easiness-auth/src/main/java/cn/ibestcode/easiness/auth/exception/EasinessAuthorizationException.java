/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.auth.exception;

import cn.ibestcode.easiness.core.exception.EasinessException;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/28 14:24
 */
public class EasinessAuthorizationException extends EasinessException {

  public EasinessAuthorizationException(String message) {
    super(message);
  }

  public EasinessAuthorizationException(String message, String... params) {
    super(message, params);
  }

  public EasinessAuthorizationException(String message, Throwable cause) {
    super(message, cause);
  }

  public EasinessAuthorizationException(String message, Throwable cause, String... params) {
    super(message, cause, params);
  }
}
