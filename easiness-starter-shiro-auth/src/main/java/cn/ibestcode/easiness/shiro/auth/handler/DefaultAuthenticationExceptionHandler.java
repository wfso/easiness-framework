/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.shiro.auth.handler;

import cn.ibestcode.easiness.exception.handler.AbstractExceptionHandler;
import cn.ibestcode.easiness.exception.properties.EasinessExceptionTipsProperties;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class DefaultAuthenticationExceptionHandler extends AbstractExceptionHandler {

  @Autowired
  EasinessExceptionTipsProperties properties;

  @ResponseBody
  @ExceptionHandler(AuthenticationException.class)
  ResponseEntity<Object> defaultAuthenticationExceptionHandle(Throwable throwable) {
    return commonExceptionHandler(throwable, properties.getCodeName(), properties.getMsgName());
  }

}
