/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.helper;

import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.order.model.OrderStatus;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
public class EasinessOrderHelper {

  /**
   * 用于生成 EasinessOrder 对象的 工具函数
   *
   * @param ownerUuid 订单所属者的唯一标识-某人或某组织
   * @param orderType 订单类型
   * @param orderName 订单名称
   * @param orderInfo 订单详细说明
   * @param currency  货币-默认为人民币CNY
   * @return EasinessOrder 对象
   */
  public static EasinessOrder getInstance(String ownerUuid, String orderType, String orderName,
                                          String orderInfo, String currency) {
    if (StringUtils.isBlank(currency)) {
      currency = "CNY";
    }
    if (StringUtils.isBlank(orderName)) {
      orderName = orderType;
    }
    EasinessOrder order = new EasinessOrder();
    order.setOwnerUuid(ownerUuid);
    order.setOrderType(orderType);
    order.setOrderName(orderName);
    order.setOrderInfo(orderInfo);
    order.setCurrency(currency);
    order.setOrderStatus(OrderStatus.UNPAID);
    return order;
  }

  /**
   * 用于生成 EasinessOrder 对象的 工具函数
   *
   * @param ownerUuid 订单所属者的唯一标识-某人或某组织
   * @param orderType 订单类型
   * @param orderName 订单名称
   * @param orderInfo 订单详细说明
   * @return EasinessOrder 对象
   */
  public static EasinessOrder getInstance(String ownerUuid, String orderType, String orderName,
                                          String orderInfo) {
    return getInstance(ownerUuid, orderType, orderName, orderInfo, "CNY");
  }

  /**
   * 用于生成 EasinessOrder 对象的 工具函数
   *
   * @param ownerUuid 订单所属者的唯一标识-某人或某组织
   * @param orderType 订单类型
   * @param orderName 订单名称
   * @return EasinessOrder 对象
   */
  public static EasinessOrder getInstance(String ownerUuid, String orderType, String orderName) {
    return getInstance(ownerUuid, orderType, orderName, null);
  }

  /**
   * 用于生成 EasinessOrder 对象的 工具函数
   *
   * @param ownerUuid 订单所属者的唯一标识-某人或某组织
   * @param orderType 订单类型
   * @return EasinessOrder 对象
   */
  public static EasinessOrder getInstance(String ownerUuid, String orderType) {
    return getInstance(ownerUuid, orderType, orderType);
  }
}
