/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.model;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import cn.ibestcode.easiness.form.domain.Form;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:29
 */
@Data
@Entity
@Table(name = "form_pattern", indexes = {
  @Index(columnList = "uuid", name = "form_pattern_uuid", unique = true)
})
public class FormPattern extends UuidBaseJpaModel implements Form<FormItem, Map<String, String>> {

  @ApiModelProperty("表单名称")
  @Column(name = "form_name", length = 100)
  private String name;

  @ApiModelProperty("表单的说明")
  private String description;

  @Transient
  private List<FormItem> items = new ArrayList<>();

}
