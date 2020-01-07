/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.event;

import cn.ibestcode.easiness.order.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Setter
@Getter
@ToString
public class OrderStatusSettingEvent implements Serializable {
  public OrderStatusSettingEvent(String orderUuid, OrderStatus orderStatus) {
    this.orderUuid = orderUuid;
    this.orderStatus = orderStatus;
  }

  public OrderStatusSettingEvent(String orderUuid, OrderStatus orderStatus, String payUuid) {
    this.orderUuid = orderUuid;
    this.orderStatus = orderStatus;
    this.payUuid = payUuid;
  }

  private String orderUuid;
  private OrderStatus orderStatus;
  private String payUuid;
}
