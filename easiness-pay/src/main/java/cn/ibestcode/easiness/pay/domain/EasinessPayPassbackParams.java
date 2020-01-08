/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 21:13
 */
@Getter
@Setter
@ToString
@ApiModel("支付平台回传参数")
public class EasinessPayPassbackParams implements Serializable {
  @ApiModelProperty("在多平台环境下，标识是哪个平台")
  private String platformId;

  @ApiModelProperty("在群集环境下，标识是哪台主机")
  private String workerId;

  @ApiModelProperty("备注信息")
  private String intro;
}
