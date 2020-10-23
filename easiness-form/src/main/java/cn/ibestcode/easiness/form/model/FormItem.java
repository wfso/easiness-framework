/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.model;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import cn.ibestcode.easiness.core.converter.MapJsonConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:29
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "form_item", indexes = {
  @Index(columnList = "uuid", name = "form_item_uuid", unique = true),
  @Index(columnList = "formUuid", name = "form_item_form_uuid")
})
public class FormItem extends UuidBaseJpaModel {

  @ApiModelProperty("类型")
  @Column(name = "item_type", length = 100)
  private String type;

  @ApiModelProperty("所属 表单（FormPattern）的 UUID")
  @Column(length = 64)
  private String formUuid;

  @ApiModelProperty("表单项的名称，对应 HTML 表单中的 name 属性")
  @Column(name = "item_name", length = 100)
  private String name;

  @ApiModelProperty("用户提交的表单数据，对应 HTML 表单中的 value 属性")
  @Column(name = "item_value")
  @Lob
  private String value;

  @ApiModelProperty("表单的其他数据，如限制规则等")
  @Lob
  @Convert(converter = MapJsonConverter.class)
  private Map<String, String> data;

}
