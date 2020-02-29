/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/12 20:29
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "yioks.pay.alipay.ftf")
public class AlipayFTFProperties extends AlipayProperties {
  public AlipayFTFProperties() {
    setProductCode("FACE_TO_FACE_PAYMENT");
  }
}
