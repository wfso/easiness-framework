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
public class OrderItemStatusSettingEvent implements Serializable {

  public OrderItemStatusSettingEvent(String orderItemUuid, OrderStatus orderItemStatus) {
    this.orderItemUuid = orderItemUuid;
    this.orderItemStatus = orderItemStatus;
  }

  private String orderItemUuid;
  private OrderStatus orderItemStatus;
}
