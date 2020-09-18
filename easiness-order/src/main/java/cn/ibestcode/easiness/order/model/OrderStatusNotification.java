/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/9/18 17:56
 */
@Entity
@Table(name = "easiness_order_status_notification", indexes = {
  @Index(name = "easiness_order_status_notification_uuid", columnList = "uuid", unique = true),
  @Index(name = "easiness_order_status_notification_order_uuid", columnList = "orderUuid"),
  @Index(name = "easiness_order_payUuid", columnList = "payUuid", unique = true),
  @Index(name = "easiness_order_orderType", columnList = "orderType"),
  @Index(name = "easiness_order_orderStatus", columnList = "orderStatus"),
  @Index(name = "easiness_order_completeAt", columnList = "completeAt"),
  @Index(name = "easiness_order_complete", columnList = "complete"),
  @Index(name = "easiness_order_canRefund", columnList = "canRefund"),
  @Index(name = "easiness_order_refundTimeout", columnList = "refundTimeout")
})
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("订单状态变更通知表")
public class OrderStatusNotification {

}
