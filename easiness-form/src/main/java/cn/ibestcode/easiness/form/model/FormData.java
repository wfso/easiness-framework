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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Lob;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:31
 */
@Data
@ApiModel("用户提交的表单数据")
public class FormData extends UuidBaseJpaModel {
  @ApiModelProperty("所属 Form 的 uuid")
  @Column(length = 64)
  private String formUuid;

  @ApiModelProperty("所属人 uuid")
  @Column(length = 64)
  private String userUuid;

  @Lob
  @ApiModelProperty("用户提交的数据")
  @Convert(converter = MapJsonConverter.class)
  private Map<String, String> data;
}
