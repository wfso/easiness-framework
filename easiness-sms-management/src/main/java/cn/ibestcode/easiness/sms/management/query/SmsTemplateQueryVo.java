/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.query;


import cn.ibestcode.easiness.core.query.builder.DefaultFiltersBuilder;
import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:18
 */
@Setter
@Getter
@ApiModel("短信模板查询Vo")
public class SmsTemplateQueryVo implements FilterGenerator {

  @ApiModelProperty("短信的Template")
  private String template;

  @ApiModelProperty("短信的Template名")
  private String templateName;

  @Override
  public IFilter generateFilter() {

    return DefaultFiltersBuilder.getAndInstance()
      .andEqual("template", template)
      .andContain("templateName", templateName)
      .build();
  }
}
