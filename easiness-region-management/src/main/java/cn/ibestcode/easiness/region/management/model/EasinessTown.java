/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.model;

import cn.ibestcode.easiness.core.base.model.BaseJpaModel;
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
 * create by WFSO (仵士杰) at 2020/01/17 20:13
 */
@Setter
@Getter
@ApiModel("乡、镇、街道")
@Table(name = "easiness_region_town", indexes = {
  @Index(name = "easiness_region_town_code", columnList = "code", unique = true),
  @Index(name = "easiness_region_town_level", columnList = "level")
})
@Entity
@ToString(callSuper = true)
public class EasinessTown extends BaseJpaModel {
  @ApiModelProperty("乡、镇、街道名称")
  @Column(length = 50)
  private String name;
  @ApiModelProperty("乡、镇、街道编码")
  @Column(length = 20, updatable = false)
  private String code;
  @ApiModelProperty("乡、镇、街道层级")
  @Column(length = 24, updatable = false)
  private String level;
}
