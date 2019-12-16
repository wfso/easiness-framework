/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.model;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:51
 */
public enum EasinessSmsStatus {
  /**
   * 未发送 （UNSEND）
   */
  UNSENT("未发送"),

  /**
   * 发送中
   */
  SENDING("发送中"),

  /**
   * 成功
   */
  SUCCESS("成功"),

  /**
   * 失败（FAILED）
   */
  FAILED("失败");


  /**
   * 文字描述
   */
  private final String text;

  EasinessSmsStatus(String text) {
    this.text = text;
  }

  public String getText() {
    return this.text;
  }
}
