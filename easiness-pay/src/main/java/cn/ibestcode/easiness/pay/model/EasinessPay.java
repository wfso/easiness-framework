/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.model;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 20:12
 */
@Entity
@Table(name = "easiness_pay", indexes = {
  @Index(name = "easiness_pay_uuid", columnList = "uuid", unique = true),
  @Index(name = "easiness_pay_payType", columnList = "payType"),
  @Index(name = "easiness_pay_payStatus", columnList = "payStatus"),
  @Index(name = "easiness_pay_offline", columnList = "offline"),
  @Index(name = "easiness_pay_payerUuid", columnList = "payerUuid"),
  @Index(name = "easiness_pay_onlineUuid", columnList = "onlineUuid"),
  @Index(name = "easiness_pay_handlerUuid", columnList = "handlerUuid"),
  @Index(name = "easiness_pay_completeAt", columnList = "completeAt"),
  @Index(name = "easiness_pay_complete", columnList = "complete")
})
@Setter
@Getter
@ToString(callSuper = true)
@ApiModel("支付表")
public class EasinessPay extends UuidBaseJpaModel {
  @ApiModelProperty("所属订单的UUID-逻辑主键")
  @Column(length = 64, updatable = false)
  private String orderUuid;

  @ApiModelProperty("支付方式")
  @Column(length = 50)
  private String payType;

  @ApiModelProperty("支付状态")
  @Enumerated(value = EnumType.STRING)
  @Column(length = 50)
  private PayStatus payStatus;

  @ApiModelProperty("货币-默认为人民币CNY")
  @Column(length = 20, updatable = false)
  private String currency;

  @ApiModelProperty("金额")
  private int price;

  @ApiModelProperty("线下支付标识")
  private boolean offline = false;

  @ApiModelProperty("在线支付的付款人")
  @Column(length = 64)
  private String payerUuid;

  @ApiModelProperty("在线支付时发起支付的客户端IP")
  @Column(length = 100)
  private String clientIp;

  @ApiModelProperty("在线支付的下单URL")
  @Column(length = 250)
  private String onlineUrl;

  @ApiModelProperty("在线支付的下单参数")
  @Lob
  private String onlineParam;

  @ApiModelProperty("在线支付时，支付平台返回数据；线下支付时，操作人提交的表单数据")
  @Lob
  private String onlineResultData;

  @ApiModelProperty("在线支付时，支付平台的订单标识；线下支付时，流水单号")
  @Column(length = 128)
  private String onlineUuid;

  @ApiModelProperty(value = "在线支付的过期时间", example = "0")
  private long expirationAt;

  @ApiModelProperty("线下支付的操作人")
  @Column(length = 64)
  private String handlerUuid;

  @ApiModelProperty("线下支付时操作人的客户端IP")
  @Column(length = 100)
  private String handlerIp;

  @ApiModelProperty("线下支付的说明，在线支付时可做他用")
  @Column(length = 250)
  private String offlineDescription;

  @ApiModelProperty(value = "支付的完成时间", example = "0")
  private Long completeAt;

  @ApiModelProperty("支付的完成标识")
  private boolean complete;
}
