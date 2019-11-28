/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.configuration.model;

import cn.ibestcode.easiness.core.base.model.BaseJpaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel(description = "通用配置")
@Table(name = "easiness_configuration",
  indexes = {
    @Index(columnList = "configKey", name = "easiness_configuration_configKey", unique = true)
  }
)
public class Configuration extends BaseJpaModel {
  @Column(length = 100)
  @ApiModelProperty("配置键名")
  private String configKey;

  @Lob
  @Column
  @ApiModelProperty("配置键值")
  private String value;

}
