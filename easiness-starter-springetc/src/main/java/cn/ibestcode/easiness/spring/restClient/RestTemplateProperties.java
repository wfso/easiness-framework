/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.spring.restClient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/14 22:15
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "easiness.spring.rest.client")
public class RestTemplateProperties {
  // 启用 Cookie 管理
  private boolean cookieManagement = true;

  // 启用授权缓存
  private boolean authCaching = true;

  private boolean automaticRetries = true;
  private boolean connectionState = true;
  private boolean contentCompression = true;
  private boolean defaultUserAgent = true;
  private boolean redirectHandling = true;
  private boolean connectionManagerShared = false;

  private String userAgent;
}
