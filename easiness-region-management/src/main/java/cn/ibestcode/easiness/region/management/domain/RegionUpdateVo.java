/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/17 20:13
 */
@Getter
@Setter
@ToString
@ApiModel("区域更新对象")
public class RegionUpdateVo {
  @NotNull
  @Size(min = 1, max = 20)
  @ApiModelProperty("行政区域编码")
  private String code;

  @NotNull
  @Size(min = 1, max = 50)
  @ApiModelProperty("区域名称")
  private String name;
}
