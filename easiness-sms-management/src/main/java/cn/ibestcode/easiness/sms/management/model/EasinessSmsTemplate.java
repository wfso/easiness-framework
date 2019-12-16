/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.model;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 23:37
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel(description = "通用配置")
@Table(name = "easiness_sms_template",
  indexes = {
    @Index(columnList = "uuid", name = "easiness_sms_template_uuid", unique = true),
    @Index(columnList = "template", name = "easiness_sms_template_template")
  }
)
public class EasinessSmsTemplate extends UuidBaseJpaModel {

  @Column(length = 20)
  @ApiModelProperty("短信的Template")
  private String template;

  @Column(length = 250)
  @ApiModelProperty("模板内容")
  private String content;

}
