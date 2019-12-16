/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.exception;

import cn.ibestcode.easiness.core.exception.EasinessException;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:45
 */
public class SmsManagementException extends EasinessException {
  public SmsManagementException(String message) {
    super(message);
  }

  public SmsManagementException(String message, String... params) {
    super(message, params);
  }

  public SmsManagementException(String message, Throwable cause) {
    super(message, cause);
  }

  public SmsManagementException(String message, Throwable cause, String... params) {
    super(message, cause, params);
  }
}
