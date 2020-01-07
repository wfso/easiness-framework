/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("订单表")
public class EasinessOrderItemUpdateVo implements Serializable {
  @ApiModelProperty("订单项的UUID")
  private String uuid;

  @ApiModelProperty("状态说明-对订单的当前状态进行说明")
  private String statusDescription;
}
