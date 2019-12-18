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
public interface SmsHookResultItem {

  /**
   * 获取短信平台的唯一标识
   *
   * @return 短信平台的唯一标识
   */
  String getSendId();

  /**
   * 获取接收短信的手机号
   *
   * @return 手机号
   */
  String getPhone();

  /**
   * 获取短信发送者的类型
   *
   * @return 短信发送者类型
   */
  String getSenderType();

  /**
   * 短信的发送状态
   *
   * @return 发送状态
   */
  EasinessSmsStatus getStatus();

  /**
   * 获取其他信息，一般为短信发送网关的状态码
   *
   * @return 发送状态简介
   */
  String getIntro();

  /**
   * 把当前对象转换成JSON字符串
   *
   * @return JSON字符串
   */
  String toJSON();

}
