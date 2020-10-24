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
import cn.ibestcode.easiness.form.domain.Item;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:29
 */
@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "form_item", indexes = {
  @Index(columnList = "uuid", name = "form_item_uuid", unique = true),
  @Index(columnList = "formUuid", name = "form_item_form_uuid")
})
@ApiModel("表单项")
public class FormItem extends UuidBaseJpaModel implements Item<Map<String, String>> {

  @ApiModelProperty("所属 表单（FormPattern）的 UUID")
  @Column(length = 64)
  private String formUuid;

  @ApiModelProperty("类型")
  @Column(name = "item_type", length = 100)
  private String type;

  @ApiModelProperty("表单项的名称，对应 HTML 表单中的 name 属性")
  @Column(name = "item_name", length = 100)
  private String name;

  @ApiModelProperty("用户提交的表单数据，对应 HTML 表单中的 value 属性")
  @Column(name = "item_value")
  @Lob
  private String value;

  @ApiModelProperty("表单项，在表单中的位置")
  private int position;

  @ApiModelProperty("表单数据（value）的限制规则（取值范围）")
  @Lob
  @Convert(converter = MapJsonConverter.class)
  @Column(name = "item_limit")
  private Map<String, String> limit = new HashMap<>();

}
