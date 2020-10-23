/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.model;

import cn.ibestcode.easiness.core.converter.MapJsonConverter;
import lombok.Data;

import javax.persistence.Convert;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:31
 */
@Data
public class FormData {
  private String formUuid;

  @Convert(converter = MapJsonConverter.class)
  private Map<String, String> data;
}
