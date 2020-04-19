/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.response.format.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/19 21:52
 */
@Data
@Component
@ConfigurationProperties(prefix = "easiness.response.format")
public class EasinessResponseFormatProperties {
  private String codeName = "code";
  private String msgName = "msg";
  private String resultName = "result";
  private String codeValue = "success";
  private String msgValue = "正常";
}
