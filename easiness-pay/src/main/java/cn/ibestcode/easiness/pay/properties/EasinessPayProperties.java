/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.properties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 20:19
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "easiness.pay")
public class EasinessPayProperties {
  @ApiModelProperty("在多平台环境下，标识是哪个平台")
  private String platformId;

  @ApiModelProperty("在群集环境下，标识是哪台主机")
  private String workerId;

  @ApiModelProperty("订单默认过期时间-2小时")
  private long expire = 7200000;
}
