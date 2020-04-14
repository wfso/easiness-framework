/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/3/1 20:04
 */
public class MapUtil {
  public static Map<String, String> monolayerObjectToMap(Object object) {
    Map<String, String> map = new HashMap<>();
    monolayerFillMap(object, map);
    return map;
  }

  public static TreeMap<String, String> monolayerObjectToTreeMap(Object object) {
    TreeMap<String, String> treeMap = new TreeMap<>();
    monolayerFillMap(object, treeMap);
    return treeMap;
  }

  protected static void monolayerFillMap(Object object, Map<String, String> map) {
    final BeanWrapper sourceWrapper = new BeanWrapperImpl(object);
    PropertyDescriptor[] pds = sourceWrapper.getPropertyDescriptors();
    for (PropertyDescriptor pd : pds) {
      Object propertyValue = sourceWrapper.getPropertyValue(pd.getName());
      if (!ObjectUtils.isEmpty(propertyValue)) {
        map.put(pd.getName(), propertyValue.toString());
      }
    }
  }
}
