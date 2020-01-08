/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.domain;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 21:13
 */
public interface PlaceOrderResult {
  /**
   * 支付平台交易订单号
   *
   * @return 字段串类型的交易订单号
   */
  String getTradeId();

  /**
   * 商户订单号
   *
   * @return 字符串类型的商户订单号
   */
  String getOutTradeId();

  /**
   * 下单是否成功
   *
   * @return 成功返回 true 否则 false
   */
  boolean isSucceed();

  /**
   * 下单返回对象的JSON
   *
   * @return JSON格式的下单返回对象
   */
  String toJSON();

  /**
   * 用户调用支付接口时，平台需要返回的数据
   *
   * @return 字符串类型的请求响应
   */
  String getResponseBody();
}
