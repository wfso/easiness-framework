/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.model;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:14
 */
@Entity
@Table(name = "easiness_order_item", indexes = {
  @Index(name = "easiness_order_item_uuid", columnList = "uuid", unique = true),
  @Index(name = "easiness_order_item_orderUuid", columnList = "orderUuid"),
  @Index(name = "easiness_order_item_orderType", columnList = "orderType"),
  @Index(name = "easiness_order_item_ownerUuid", columnList = "ownerUuid"),
  @Index(name = "easiness_order_item_objectUuid", columnList = "objectUuid"),
  @Index(name = "easiness_order_item_orderStatus", columnList = "orderStatus"),
  @Index(name = "easiness_order_item_completeAt", columnList = "completeAt"),
  @Index(name = "easiness_order_item_complete", columnList = "complete"),
  @Index(name = "easiness_order_item_canRefund", columnList = "canRefund"),
  @Index(name = "easiness_order_item_refundTimeout", columnList = "refundTimeout")
})
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("订单项表")
public class EasinessOrderItem extends UuidBaseJpaModel {
  @ApiModelProperty("所属订单")
  @Column(length = 32, updatable = false)
  private String orderUuid;

  @ApiModelProperty("订单类型")
  @Column(length = 100, updatable = false)
  private String orderType;

  @ApiModelProperty("订单所属者的uuid-某人或某组织")
  @Column(length = 64, updatable = false)
  private String ownerUuid;

  @ApiModelProperty("订单关联对象的UUID-某商品唯一标识")
  @Column(length = 64, updatable = false)
  private String objectUuid;

  @ApiModelProperty("订单关联对象的名称-某商品名称")
  @Column(length = 200, updatable = false)
  private String objectName;

  @ApiModelProperty("订单关联对象的URL-某商品网址")
  @Column(length = 250, updatable = false)
  private String objectUrl;

  @ApiModelProperty("订单关联对象的封面图-某商品封面图")
  @Column(length = 250, updatable = false)
  private String objectImage;

  @ApiModelProperty("订单关联对象的其他展示信息-JSON格式")
  @Column(updatable = false)
  @Lob
  private String objectData;

  @ApiModelProperty("商品单价-单位为对应币种的最小可支付单位，人民币为：分")
  @Column(updatable = false)
  private int unitPrice;

  @ApiModelProperty("商品数量")
  private int quantity;

  @ApiModelProperty("总金额-单位为对应币种的最小可支付单位，人民币为：分")
  private int totalPrice;


  @ApiModelProperty("实际金额-单位为对应币种的最小可支付单位，人民币为：分")
  private int realPrice;

  @ApiModelProperty("实际金额的计算规则")
  @Column(length = 100)
  private String realPriceRule;

  @ApiModelProperty("货币-默认为人民币CNY")
  @Column(length = 20, updatable = false)
  private String currency;

  @ApiModelProperty("订单的状态")
  @Enumerated(value = EnumType.STRING)
  @Column(length = 50)
  private OrderStatus orderStatus;

  @ApiModelProperty("订单完成时间")
  private Long completeAt;

  @ApiModelProperty("完成标识")
  private boolean complete;

  @ApiModelProperty("可退款时间限制")
  private long refundTimeout;

  @ApiModelProperty("可退款标识")
  private boolean canRefund;

  @ApiModelProperty("冗余：订单项名称")
  @Column(length = 200, updatable = false)
  private String orderItemName;

  @ApiModelProperty("冗余：订单项详细说明")
  @Column(updatable = false)
  @Lob
  private String orderItemInfo;

  @ApiModelProperty("状态说明-对订单的当前状态进行说明")
  @Column(length = 200)
  private String statusDescription;

  public boolean isCanRefund(){
    canRefund = refundTimeout > System.currentTimeMillis();
    return canRefund;
  }
}
