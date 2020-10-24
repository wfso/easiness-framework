/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.domain;

import cn.ibestcode.easiness.form.limit.Limit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 15:35
 */
@Data
@ApiModel("表单项")
public class DefaultItem implements Item<Limit> {
  @ApiModelProperty("所属Form的唯一标识")
  private String formUuid;
  @ApiModelProperty("表单项的名称，对应 HTML 表单中的 name 属性")
  private String name;
  @ApiModelProperty("用户提交的表单数据，对应 HTML 表单中的 value 属性")
  private String value;
  @ApiModelProperty("表单项，在表单中的位置")
  private int position;
  @ApiModelProperty("表单数据（value）的限制规则（取值范围）")
  private Limit limit;

}
