/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.response.format.advice;

import cn.ibestcode.easiness.response.format.annotation.ResponseFormat;
import cn.ibestcode.easiness.response.format.properties.EasinessResponseFormatProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/19 19:34
 */
@Slf4j
@RestControllerAdvice
public class DefaultResponseFormat implements ResponseBodyAdvice<Object> {

  @Autowired
  private EasinessResponseFormatProperties properties;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    ResponseFormat responseFormat = returnType.getExecutable().getAnnotation(ResponseFormat.class);
    if (responseFormat == null) {
      responseFormat = returnType.getContainingClass().getAnnotation(ResponseFormat.class);
    }
    if (responseFormat == null) {
      return false;
    }
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request,
                                ServerHttpResponse response) {

    ResponseFormat responseFormat = returnType.getExecutable().getAnnotation(ResponseFormat.class);
    if (responseFormat == null) {
      responseFormat = returnType.getContainingClass().getAnnotation(ResponseFormat.class);
    }
    if (responseFormat == null) {
      return body;
    }

    String codeName = responseFormat.codeName();
    if (StringUtils.isBlank(codeName)) {
      codeName = properties.getCodeName();
    }

    String msgName = responseFormat.msgName();
    if (StringUtils.isBlank(msgName)) {
      msgName = properties.getMsgName();
    }

    String resultName = responseFormat.resultName();
    if (StringUtils.isBlank(resultName)) {
      resultName = properties.getResultName();
    }

    Map<String, Object> result = new HashMap<>();

    result.put(resultName, body);
    result.put(codeName, properties.getCodeValue());
    result.put(msgName, properties.getMsgValue());

    if (StringHttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
      try {
        return objectMapper.writeValueAsString(result);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        log.warn(e.getMessage());
        return body;
      }
    }
    return result;
  }
}
