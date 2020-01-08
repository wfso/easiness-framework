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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 20:13
 */
@Entity
@Table(name = "easiness_pay_extend", indexes = {
  @Index(name = "easiness_pay_extend_uuid", columnList = "uuid", unique = true),
  @Index(name = "easiness_pay_extend_payUuid", columnList = "payUuid")
})
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("支付扩展表")
public class EasinessPayExtend extends UuidBaseJpaModel {

  @ApiModelProperty("所属支付")
  @Column(length = 32)
  private String payUuid;

  @ApiModelProperty("键")
  @Column(length = 100)
  private String extendKey;

  @ApiModelProperty("键名")
  @Column(length = 100)
  private String keyName;

  @ApiModelProperty("值")
  @Column(length = 250)
  private String value;
}
