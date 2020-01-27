/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.domain;

import cn.ibestcode.easiness.core.query.builder.DefaultFiltersBuilder;
import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/17 20:13
 */
@Setter
@Getter
@ToString
@ApiModel("区域查询对象")
public class RegionQueryVo implements FilterGenerator {
  @ApiModelProperty("区域的code")
  @Size(min = 1, max = 20)
  private String code;

  @ApiModelProperty("区域的名称")
  @Size(min = 1, max = 50)
  private String name;

  @ApiModelProperty("区域的层级")
  @Size(min = 1, max = 50)
  private String level;

  @Override
  public IFilter generateFilter() {
    return DefaultFiltersBuilder.getAndInstance()
      .andStartWith("code", code)
      .andStartWith("level", level)
      .andContain("name", name)
      .build();
  }
}
