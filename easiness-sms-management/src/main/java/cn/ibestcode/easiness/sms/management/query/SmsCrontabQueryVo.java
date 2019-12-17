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
import cn.ibestcode.easiness.sendsms.hook.EasinessSmsStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:18
 */
@Getter
@Setter
@ApiModel("定时短信查询Vo")
public class SmsCrontabQueryVo implements FilterGenerator {

  @ApiModelProperty("短信的Template")
  private String template;

  @ApiModelProperty("手机号")
  private String phone;

  @ApiModelProperty("发送状态")
  private EasinessSmsStatus smsStatus;

  @Override
  public IFilter generateFilter() {
    return DefaultFiltersBuilder.getAndInstance()
      .andEqual("template", template)
      .andEqual("phone", phone)
      .andEqual("smsStatus", smsStatus.name(), EasinessSmsStatus.class)
      .build();
  }
}
