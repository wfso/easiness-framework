/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.core.converter;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 12:07
 */
@Slf4j
public class ClassStringConverter implements AttributeConverter<Class, String> {
  @Override
  public String convertToDatabaseColumn(Class attribute) {
    return attribute.getName();
  }

  @Override
  public Class convertToEntityAttribute(String dbData) {
    try {
      return Class.forName(dbData);
    } catch (ClassNotFoundException e) {
      log.warn(e.getMessage(), e);
    }
    return null;
  }
}
