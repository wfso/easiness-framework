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

import javax.persistence.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/17 20:13
 */
@Setter
@Getter
@ApiModel("区域")
@Table(name = "easiness_region_region", indexes = {
  @Index(name = "easiness_region_region_code", columnList = "code", unique = true),
  @Index(name = "easiness_region_region_level", columnList = "level"),
  @Index(name = "easiness_region_region_parentId", columnList = "parentId"),
  @Index(name = "easiness_region_region_regionType", columnList = "regionType")
})
@Entity
@ToString(callSuper = true)
public class EasinessRegion extends BaseJpaModel {
  @ApiModelProperty("父区域的id")
  @Column(updatable = false)
  private long parentId;

  @ApiModelProperty("区域名称")
  @Column(length = 50)
  private String name;

  @ApiModelProperty("区域类型")
  @Column(length = 20, updatable = false)
  @Enumerated(value = EnumType.STRING)
  private EasinessRegionType regionType;

  @ApiModelProperty("区域编码")
  @Column(length = 20, updatable = false)
  private String code;

  @ApiModelProperty("区域层级")
  @Column(length = 50, updatable = false)
  private String level;
}
