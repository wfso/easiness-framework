/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.domain;

import cn.ibestcode.easiness.order.model.EasinessOrderItem;
import cn.ibestcode.easiness.order.model.EasinessOrderItemExtend;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel("订单项备份")
public class EasinessOrderItemBackupVo extends EasinessOrderItem {
  @ApiModelProperty("订单项扩展列表")
  private List<EasinessOrderItemExtend> orderItemExtends;
}
