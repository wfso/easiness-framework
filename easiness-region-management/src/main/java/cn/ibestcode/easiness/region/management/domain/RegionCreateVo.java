/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.domain;

import cn.ibestcode.easiness.region.management.model.EasinessRegionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/17 20:13
 */
@Setter
@Getter
@ToString
@ApiModel("区域创建对象")
public class RegionCreateVo {
  @ApiModelProperty("父区域的id")
  @NotNull
  @Min(1)
  private long parentId;

  @ApiModelProperty("区域名称")
  @NotNull
  @Size(min = 1, max = 50)
  private String name;

  @ApiModelProperty("区域类型")
  @Enumerated(value = EnumType.STRING)
  private EasinessRegionType regionType;

  @ApiModelProperty("区域编码")
  @NotNull
  @Size(min = 1, max = 20)
  private String code;

}
