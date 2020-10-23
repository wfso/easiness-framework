/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 15:35
 */
@Data
public abstract class AbstractItem implements Item {
  @ApiModelProperty("所属Form的唯一标识")
  private String formUuid;
  @ApiModelProperty("表单项的名称，对应 HTML 表单中的 name 属性")
  private String name;
  @ApiModelProperty("用户提交的表单数据，对应 HTML 表单中的 value 属性")
  private String value;

  public boolean checkValue() {
    return true;
  }
}
