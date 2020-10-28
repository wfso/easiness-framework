/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.model;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:29
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "form_pattern", indexes = {
  @Index(columnList = "uuid", name = "form_pattern_uuid", unique = true)
})
@ApiModel("表单模式")
public class FormPattern extends UuidBaseJpaModel {

  @ApiModelProperty("表单名称")
  @Column(name = "form_name", length = 100)
  private String name;

  @ApiModelProperty("所属人 uuid")
  @Column(length = 64)
  private String userUuid;

  @ApiModelProperty("表单的说明")
  private String description;

}
