/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.session.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Getter
@Setter
@ConfigurationProperties("easiness.session")
public class EasinessSessionProperties {

  private String tokenIdentification = "EASINESS-SHIRO-TOKEN";

  private long timeout = 1800000;

  // 必须小于 timeout 的四倍
  private long sessionValidationInterval = 1800000;

  private String redissonStoreName = "EASINESS-SHIRO-SESSION-REDISSON-STORE";

  private String loginTokenName = "EASINESS-SHIRO-SESSION-LOGIN-TOKEN";

}
