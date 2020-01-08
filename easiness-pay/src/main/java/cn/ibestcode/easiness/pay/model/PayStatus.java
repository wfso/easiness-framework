/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.model;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 20:15
 */
public enum PayStatus {
  DURING("支付中"),
  PAID("已付款"),
  TIMEOUT("超时"),
  CANCEL("取消"),
  COMPLETE("完成");

  private String text;

  public String getText() {
    return text;
  }

  private PayStatus(String text) {
    this.text = text;
  }
}
