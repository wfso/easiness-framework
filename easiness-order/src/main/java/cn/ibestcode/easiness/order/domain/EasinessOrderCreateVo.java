/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.domain;

import cn.ibestcode.easiness.order.helper.EasinessOrderExtendHelper;
import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.order.model.EasinessOrderExtend;
import cn.ibestcode.easiness.order.model.EasinessOrderItem;
import cn.ibestcode.easiness.order.model.EasinessOrderPayableRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Getter
@Setter
@ToString
@ApiModel("添加订单的VO")
public class EasinessOrderCreateVo implements Serializable {
  public EasinessOrderCreateVo() {

  }

  public EasinessOrderCreateVo(EasinessOrder easinessOrder, List<EasinessOrderItem> items) {
    this.easinessOrder = easinessOrder;
    for (EasinessOrderItem item : items) {
      orderItemCreateVos.add(new EasinessOrderItemCreateVo(item));
    }
  }

  public EasinessOrderCreateVo(EasinessOrder easinessOrder, List<EasinessOrderItem> items, List<EasinessOrderPayableRule> payableRules) {
    this(easinessOrder, items);
    this.payableRules = payableRules;
  }

  public EasinessOrderCreateVo(EasinessOrder easinessOrder, List<EasinessOrderItem> items, List<EasinessOrderPayableRule> payableRules, List<EasinessOrderExtend> orderExtends) {
    this(easinessOrder, items, payableRules);
    this.orderExtends = orderExtends;
  }

  public EasinessOrderCreateVo(EasinessOrder easinessOrder, List<EasinessOrderItem> items, List<EasinessOrderPayableRule> payableRules, Map<String, String> map) {
    this(easinessOrder, items, payableRules, EasinessOrderExtendHelper.getInstanceList(map));
  }

  public EasinessOrderCreateVo(EasinessOrder easinessOrder, List<EasinessOrderItem> items, Map<String, String> map) {
    this(easinessOrder, items, null, map);
  }

  @ApiModelProperty("订单对象")
  @NotNull
  private EasinessOrder easinessOrder;

  @ApiModelProperty("订单对象下的订单项列表")
  @NotNull
  @NotEmpty
  private List<EasinessOrderItemCreateVo> orderItemCreateVos = new ArrayList<>();

  @ApiModelProperty("订单的扩展列表")
  private List<EasinessOrderExtend> orderExtends = new ArrayList<>();

  @ApiModelProperty("订单对象可能用到的应支付金额计算规则")
  private List<EasinessOrderPayableRule> payableRules = new ArrayList<>();
}
