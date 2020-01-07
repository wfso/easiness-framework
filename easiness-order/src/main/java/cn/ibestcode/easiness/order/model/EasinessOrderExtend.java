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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:21
 */
@Entity
@Table(name = "easiness_order_extend", indexes = {
  @Index(name = "easiness_order_extend_uuid", columnList = "uuid", unique = true),
  @Index(name = "easiness_order_extend_orderUuid", columnList = "orderUuid")
})
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("订单扩展表")
public class EasinessOrderExtend extends UuidBaseJpaModel {

  @ApiModelProperty("所属订单")
  @Column(length = 32)
  private String orderUuid;

  @ApiModelProperty("扩展的键")
  @Column(length = 100)
  private String extendKey;

  @ApiModelProperty("键名")
  @Column(length = 100)
  private String keyName;

  @ApiModelProperty("值")
  @Column(length = 250)
  private String value;
}
