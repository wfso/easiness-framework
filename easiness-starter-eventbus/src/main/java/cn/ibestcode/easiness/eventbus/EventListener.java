/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus;

import cn.ibestcode.easiness.eventbus.annotation.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/25 19:56
 */
@Listener
@Autowired
@Component
@Inherited
@Documented
@Qualifier("EventListener")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface EventListener {
  @AliasFor(
    annotation = Component.class
  )
  String value() default "";
}
