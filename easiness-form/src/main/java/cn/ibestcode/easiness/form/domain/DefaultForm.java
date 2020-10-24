/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.domain;

import cn.ibestcode.easiness.form.limit.Limit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/24 13:21
 */
@Data
public class DefaultForm implements Form<DefaultItem, Limit> {
  @ApiModelProperty("唯一标识")
  private String uuid;
  @ApiModelProperty("表单名称")
  private String name;
  @ApiModelProperty("表单的说明")
  private String description;
  @ApiModelProperty("表单项列表")
  private List<DefaultItem> items = new ArrayList<>();
}