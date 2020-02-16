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
public interface PayAsyncNotification {

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
   * 支付平台的卖家标识
   *
   * @return 字符串类型的卖家标识
   */
  String getSellerId();

  /**
   * 支付平台的买家标识
   *
   * @return 字符串类型的买家标识
   */
  String getBuyerId();

  /**
   * 交易金额
   *
   * @return 字符类型的交易金额
   */
  String getTotalAmount();

  /**
   * 支付平台分配给开发者的应用ID
   *
   * @return 字符串格式的应用ID
   */
  String getAppId();

  /**
   * EasinessFramework 框架下业务平台的标识
   * 在多平台情况下，需要由总平台对支付通知进行分发时使用
   * 用于区分不同的业务平台
   * 需要由用户设计分发规则
   *
   * @return 字符串格式的支付工人标识
   */
  String getPlatformId();

  /**
   * EasinessFramework 框架下业务服务器的标识
   * 在集群环境下，用于区分不同的业务服务器
   * 需要由用户设计分发规则
   *
   * @return 字符串格式的支付工人标识
   */
  String getWorkerId();

  /**
   * 支付是否成功
   *
   * @return 成功返回 true 否则 false
   */
  boolean isSucceed();

  /**
   * 是否曾关闭交易
   *
   * @return 成功返回 true 否则 false
   */
  boolean isClosed();

  /**
   * 通知对象的JSON
   *
   * @return JSON格式的下单返回对象
   */
  String toJSON();
}
