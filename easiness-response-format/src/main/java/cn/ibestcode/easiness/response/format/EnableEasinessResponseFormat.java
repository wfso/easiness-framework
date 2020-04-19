/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.response.format;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/19 21:33
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EasinessResponseFormatConfiguration.class)
public @interface EnableEasinessResponseFormat {
}
