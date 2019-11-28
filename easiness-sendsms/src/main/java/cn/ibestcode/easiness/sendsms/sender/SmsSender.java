/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.sender;

import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/20 19:13
 */
public interface SmsSender {
  SmsSenderResult sendSms(String phone, String template, Map<String, String> vars);

  default SmsSenderResult sendSms(String phone, String template, Map<String, String> vars, String type) {
    return sendSms(phone, template, vars);
  }
}
