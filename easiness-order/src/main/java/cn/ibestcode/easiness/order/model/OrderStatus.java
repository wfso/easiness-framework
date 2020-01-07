/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.model;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
public enum OrderStatus {

  UNPAID("未付款"),
  DURING("支付中"),
  PAID("已付款"),
  CANCEL("取消订单"),
  REFUND("全部退款"),
  REFUNDING("全部退款中"),
  PART_REFUND("部分退款"),
  PART_REFUNDING("部分退款中"),
  COMPLETE("完成");


  private String text;

  public String getText() {
    return text;
  }

  private OrderStatus(String text) {
    this.text = text;
  }
}
