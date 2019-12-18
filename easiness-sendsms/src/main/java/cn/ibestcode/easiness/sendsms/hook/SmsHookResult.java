/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.hook;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:49
 */
public interface SmsHookResult {

  /**
   * 每个 SmsHookResultItem 对象表示一个短信的
   * hook 返回值
   *
   * @return SmsHookResultItem 列表
   */
  List<SmsHookResultItem> getItems();

  /**
   * 把当前对象转换成JSON字符串
   *
   * @return JSON字符串
   */
  String toJSON();

  /**
   * 需要向hook的响应
   *
   * @return hook 响应体
   */
  String getResponse();
}
