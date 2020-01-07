/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.helper;

import cn.ibestcode.easiness.order.model.EasinessOrderItem;
import cn.ibestcode.easiness.order.model.OrderStatus;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
public class EasinessOrderItemHelper {

  /**
   * 用于生成 EasinessOrderItem 对象的 工具函数
   *
   * @param objectUuid    订单项所关联商品对象的唯一标识
   * @param objectName    订单项所关联商品对象的名称
   * @param objectUrl     订单项所关联商品对象的URL
   * @param unitPrice     商品单价
   * @param quantity      商品数量
   * @param totalPrice    商品总价
   * @param realPrice     购买商品需要的实际金额
   * @param realPriceRule 商品实际金额的计算规则
   * @param currency      货币类型
   * @param orderItemName 订单项的名称
   * @param orderItemInfo 订单项的详细说明
   * @param objectImage   订单项所关联商品的封面图片URL
   * @param objectData    订单项所关联商品的其他数据-JSON格式
   * @return EasinessOrderItem 实例
   */
  public static EasinessOrderItem getInstance(String objectUuid, String objectName, String objectUrl,
                                              int unitPrice, int quantity, int totalPrice, int realPrice,
                                              String realPriceRule, String currency, String orderItemName,
                                              String orderItemInfo, String objectImage, String objectData) {
    if (StringUtils.isBlank(currency)) {
      currency = "CNY";
    }
    if (quantity <= 0) {
      quantity = 1;
    }
    if (totalPrice <= 0) {
      totalPrice = unitPrice * quantity;
    }
    if (realPrice <= 0) {
      realPrice = totalPrice;
    }
    if (StringUtils.isBlank(orderItemName)) {
      orderItemName = objectName;
    }
    EasinessOrderItem item = new EasinessOrderItem();
    item.setObjectUuid(objectUuid);
    item.setObjectName(objectName);
    item.setObjectUrl(objectUrl);
    item.setUnitPrice(unitPrice);
    item.setQuantity(quantity);
    item.setTotalPrice(totalPrice);
    item.setRealPrice(realPrice);
    item.setRealPriceRule(realPriceRule);
    item.setCurrency(currency);
    item.setOrderItemName(orderItemName);
    item.setOrderItemInfo(orderItemInfo);
    item.setObjectImage(objectImage);
    item.setObjectData(objectData);
    item.setOrderStatus(OrderStatus.UNPAID);
    return item;
  }


  /**
   * 用于生成 EasinessOrderItem 对象的 工具函数
   *
   * @param objectUuid    订单项所关联商品对象的唯一标识
   * @param objectName    订单项所关联商品对象的名称
   * @param objectUrl     订单项所关联商品对象的URL
   * @param unitPrice     商品单价
   * @param quantity      商品数量
   * @param totalPrice    商品总价
   * @param realPrice     购买商品需要的实际金额
   * @param realPriceRule 商品实际金额的计算规则
   * @param currency      货币类型
   * @param orderItemName 订单项的名称
   * @param orderItemInfo 订单项的详细说明
   * @param objectImage   订单项所关联商品的封面图片URL
   * @return EasinessOrderItem 实例
   */
  public static EasinessOrderItem getInstance(String objectUuid, String objectName, String objectUrl,
                                              int unitPrice, int quantity, int totalPrice, int realPrice,
                                              String realPriceRule, String currency, String orderItemName,
                                              String orderItemInfo, String objectImage) {

    return getInstance(objectUuid, objectName, objectUrl, unitPrice, quantity, totalPrice, realPrice,
      realPriceRule, currency, orderItemName, orderItemInfo, objectImage, null);
  }


  /**
   * 用于生成 EasinessOrderItem 对象的 工具函数
   *
   * @param objectUuid    订单项所关联商品对象的唯一标识
   * @param objectName    订单项所关联商品对象的名称
   * @param objectUrl     订单项所关联商品对象的URL
   * @param unitPrice     商品单价
   * @param quantity      商品数量
   * @param totalPrice    商品总价
   * @param realPrice     购买商品需要的实际金额
   * @param realPriceRule 商品实际金额的计算规则
   * @param currency      货币类型
   * @param orderItemName 订单项的名称
   * @param orderItemInfo 订单项的详细说明
   * @return EasinessOrderItem 实例
   */
  public static EasinessOrderItem getInstance(String objectUuid, String objectName, String objectUrl,
                                              int unitPrice, int quantity, int totalPrice, int realPrice,
                                              String realPriceRule, String currency, String orderItemName,
                                              String orderItemInfo) {

    return getInstance(objectUuid, objectName, objectUrl, unitPrice, quantity, totalPrice, realPrice,
      realPriceRule, currency, orderItemName, orderItemInfo, null);
  }

  /**
   * 用于生成 EasinessOrderItem 对象的 工具函数
   *
   * @param objectUuid    订单项所关联商品对象的唯一标识
   * @param objectName    订单项所关联商品对象的名称
   * @param objectUrl     订单项所关联商品对象的URL
   * @param unitPrice     商品单价
   * @param quantity      商品数量
   * @param totalPrice    商品总价
   * @param realPrice     购买商品需要的实际金额
   * @param realPriceRule 商品实际金额的计算规则
   * @param currency      货币类型
   * @param orderItemName 订单项的名称
   * @return EasinessOrderItem 实例
   */
  public static EasinessOrderItem getInstance(String objectUuid, String objectName, String objectUrl,
                                              int unitPrice, int quantity, int totalPrice, int realPrice,
                                              String realPriceRule, String currency, String orderItemName) {

    return getInstance(objectUuid, objectName, objectUrl, unitPrice, quantity, totalPrice, realPrice,
      realPriceRule, currency, orderItemName, null);
  }

  /**
   * 用于生成 EasinessOrderItem 对象的 工具函数
   *
   * @param objectUuid    订单项所关联商品对象的唯一标识
   * @param objectName    订单项所关联商品对象的名称
   * @param objectUrl     订单项所关联商品对象的URL
   * @param unitPrice     商品单价
   * @param quantity      商品数量
   * @param totalPrice    商品总价
   * @param realPrice     购买商品需要的实际金额
   * @param realPriceRule 商品实际金额的计算规则
   * @param currency      货币类型
   * @return EasinessOrderItem 实例
   */
  public static EasinessOrderItem getInstance(String objectUuid, String objectName, String objectUrl,
                                              int unitPrice, int quantity, int totalPrice, int realPrice,
                                              String realPriceRule, String currency) {

    return getInstance(objectUuid, objectName, objectUrl, unitPrice, quantity, totalPrice, realPrice,
      realPriceRule, currency, objectName);
  }

  /**
   * 用于生成 EasinessOrderItem 对象的 工具函数
   *
   * @param objectUuid    订单项所关联商品对象的唯一标识
   * @param objectName    订单项所关联商品对象的名称
   * @param objectUrl     订单项所关联商品对象的URL
   * @param unitPrice     商品单价
   * @param quantity      商品数量
   * @param totalPrice    商品总价
   * @param realPrice     购买商品需要的实际金额
   * @param realPriceRule 商品实际金额的计算规则
   * @return EasinessOrderItem 实例
   */
  public static EasinessOrderItem getInstance(String objectUuid, String objectName, String objectUrl, int unitPrice,
                                              int quantity, int totalPrice, int realPrice, String realPriceRule) {

    return getInstance(objectUuid, objectName, objectUrl, unitPrice, quantity, totalPrice, realPrice,
      realPriceRule, "CNY");
  }

  /**
   * 用于生成 EasinessOrderItem 对象的 工具函数
   *
   * @param objectUuid 订单项所关联商品对象的唯一标识
   * @param objectName 订单项所关联商品对象的名称
   * @param objectUrl  订单项所关联商品对象的URL
   * @param unitPrice  商品单价
   * @param quantity   商品数量
   * @param totalPrice 商品总价
   * @param realPrice  购买商品需要的实际金额
   * @return EasinessOrderItem 实例
   */
  public static EasinessOrderItem getInstance(String objectUuid, String objectName, String objectUrl,
                                              int unitPrice, int quantity, int totalPrice, int realPrice) {

    return getInstance(objectUuid, objectName, objectUrl, unitPrice, quantity, totalPrice, realPrice,
      null);
  }

  /**
   * 用于生成 EasinessOrderItem 对象的 工具函数
   *
   * @param objectUuid 订单项所关联商品对象的唯一标识
   * @param objectName 订单项所关联商品对象的名称
   * @param objectUrl  订单项所关联商品对象的URL
   * @param unitPrice  商品单价
   * @param quantity   商品数量
   * @return EasinessOrderItem 实例
   */
  public static EasinessOrderItem getInstance(String objectUuid, String objectName, String objectUrl,
                                              int unitPrice, int quantity) {

    return getInstance(objectUuid, objectName, objectUrl, unitPrice, quantity, unitPrice * quantity,
      unitPrice * quantity);
  }

  /**
   * 用于生成 EasinessOrderItem 对象的 工具函数
   *
   * @param objectUuid 订单项所关联商品对象的唯一标识
   * @param objectName 订单项所关联商品对象的名称
   * @param objectUrl  订单项所关联商品对象的URL
   * @param unitPrice  商品单价
   * @return EasinessOrderItem 实例
   */
  public static EasinessOrderItem getInstance(String objectUuid, String objectName,
                                              String objectUrl, int unitPrice) {
    return getInstance(objectUuid, objectName, objectUrl, unitPrice, 1);
  }
}
