/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.hook;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:49
 */
public interface SmsHookResult {

  String getSendId();

  String getPhone();

  String getSenderType();

  EasinessSmsStatus getStatus();

  String getIntro();

  String toJSON();

  String getResponse();
}
