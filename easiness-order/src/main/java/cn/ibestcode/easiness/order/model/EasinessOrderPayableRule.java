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
 * create by WFSO (仵士杰) at 2020/01/06 20:15
 */
@Entity
@Table(name = "easiness_order_payable_rule", indexes = {
  @Index(name = "easiness_order_payable_rule_uuid", columnList = "uuid", unique = true),
  @Index(name = "easiness_order_payable_rule_payableType", columnList = "payableType"),
  @Index(name = "easiness_order_payable_rule_orderUuid", columnList = "orderUuid")
})
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("订单应支付金额计算规则")
public class EasinessOrderPayableRule extends UuidBaseJpaModel implements Comparable<EasinessOrderPayableRule> {
  @ApiModelProperty("类型-对应于处理程序")
  @Column(length = 100, updatable = false)
  private String payableType;

  @ApiModelProperty("数据-处理程序需要用到的数据")
  @Lob
  private String payableData;

  @ApiModelProperty("所属订单")
  @Column(length = 32)
  private String orderUuid;

  @ApiModelProperty("名称")
  @Column(length = 200)
  private String name;

  @ApiModelProperty("生效规则说明")
  @Column(length = 200)
  private String rule;

  @ApiModelProperty("操作说明")
  @Column(length = 20)
  private String operation;

  @ApiModelProperty("生效顺序（权重）-值越小越早生效")
  private int weight = 0;

  @ApiModelProperty("生效标识-默认只要加上，都是生效的，不生效的理论上都需要删除掉")
  private boolean available = true;

  @Override
  public int compareTo(EasinessOrderPayableRule o) {
    return Integer.compare(weight, o.getWeight());
  }
}
