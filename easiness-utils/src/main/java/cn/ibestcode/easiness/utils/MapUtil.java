/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.utils;

import org.springframework.beans.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/3/1 20:04
 */
public class MapUtil {
  public static Map<String, String> monolayerObjectToMap(Object object) {
    Map<String, String> map = new HashMap<>();
    monolayerFillMap(object, map, null);
    return map;
  }

  public static TreeMap<String, String> monolayerObjectToTreeMap(Object object) {
    TreeMap<String, String> treeMap = new TreeMap<>();
    monolayerFillMap(object, treeMap, null);
    return treeMap;
  }

  public static Map<String, String> monolayerObjectToMap(Object object, Class<?> editable) {
    Map<String, String> map = new HashMap<>();
    monolayerFillMap(object, map, editable);
    return map;
  }

  public static TreeMap<String, String> monolayerObjectToTreeMap(Object object, Class<?> editable) {
    TreeMap<String, String> treeMap = new TreeMap<>();
    monolayerFillMap(object, treeMap, editable);
    return treeMap;
  }


  public static Map<String, String> monolayerObjectToMap(Object object, String... ignoreProperties) {
    Map<String, String> map = new HashMap<>();
    monolayerFillMap(object, map, null, ignoreProperties);
    return map;
  }

  public static TreeMap<String, String> monolayerObjectToTreeMap(Object object, String... ignoreProperties) {
    TreeMap<String, String> treeMap = new TreeMap<>();
    monolayerFillMap(object, treeMap, null, ignoreProperties);
    return treeMap;
  }

  protected static void monolayerFillMap(Object object, Map<String, String> map, Class<?> editable, String... ignoreProperties) {
    Class<?> actualEditable = object.getClass();
    if (editable != null) {
      if (!editable.isInstance(object)) {
        throw new IllegalArgumentException("Target class [" + object.getClass().getName() +
          "] not assignable to Editable class [" + editable.getName() + "]");
      }
      actualEditable = editable;
    }
    final BeanWrapper sourceWrapper = new BeanWrapperImpl(object);
    PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(actualEditable);
    List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
    for (PropertyDescriptor pd : pds) {
      if ((ClassUtils.isPrimitiveOrWrapper(pd.getPropertyType()) || String.class.isAssignableFrom(pd.getPropertyType()))
        && (ignoreList == null || !ignoreList.contains(pd.getName()))) {
        Object propertyValue = sourceWrapper.getPropertyValue(pd.getName());
        if (propertyValue != null) {
          map.put(pd.getName(), propertyValue.toString());
        }
      }
    }
  }


  public static Map<String, Object> objectToMap(Object object) {
    Map<String, Object> map = new HashMap<>();
    fillMap(object, map, null);
    return map;
  }

  public static TreeMap<String, Object> objectToTreeMap(Object object) {
    TreeMap<String, Object> treeMap = new TreeMap<>();
    fillMap(object, treeMap, null);
    return treeMap;
  }

  public static Map<String, Object> objectToMap(Object object, Class<?> editable) {
    Map<String, Object> map = new HashMap<>();
    fillMap(object, map, editable);
    return map;
  }

  public static TreeMap<String, Object> objectToTreeMap(Object object, Class<?> editable) {
    TreeMap<String, Object> treeMap = new TreeMap<>();
    fillMap(object, treeMap, editable);
    return treeMap;
  }

  public static Map<String, Object> objectToMap(Object object, String... ignoreProperties) {
    Map<String, Object> map = new HashMap<>();
    fillMap(object, map, null, ignoreProperties);
    return map;
  }

  public static TreeMap<String, Object> objectToTreeMap(Object object, String... ignoreProperties) {
    TreeMap<String, Object> treeMap = new TreeMap<>();
    fillMap(object, treeMap, null, ignoreProperties);
    return treeMap;
  }


  protected static void fillMap(Object object, Map<String, Object> map, @Nullable Class<?> editable, @Nullable String... ignoreProperties) {
    Class<?> actualEditable = object.getClass();
    if (editable != null) {
      if (!editable.isInstance(object)) {
        throw new IllegalArgumentException("Target class [" + object.getClass().getName() +
          "] not assignable to Editable class [" + editable.getName() + "]");
      }
      actualEditable = editable;
    }
    final BeanWrapper sourceWrapper = new BeanWrapperImpl(object);
    PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(actualEditable);
    List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
    for (PropertyDescriptor pd : pds) {
      if (ignoreList == null || !ignoreList.contains(pd.getName())) {
        Object propertyValue = sourceWrapper.getPropertyValue(pd.getName());
        map.put(pd.getName(), propertyValue);
      }
    }
  }


  protected static void mapToObject(Map source, Object target, @Nullable Class<?> editable,
                                    @Nullable String... ignoreProperties) throws BeansException {
    Assert.notNull(source, "Source must not be null");
    Assert.notNull(target, "Target must not be null");

    Class<?> actualEditable = target.getClass();
    if (editable != null) {
      if (!editable.isInstance(target)) {
        throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
          "] not assignable to Editable class [" + editable.getName() + "]");
      }
      actualEditable = editable;
    }
    PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
    List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

    for (PropertyDescriptor targetPd : targetPds) {
      Method writeMethod = targetPd.getWriteMethod();
      if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
        Object value = source.get(targetPd.getName());
        Class writeClass = writeMethod.getParameterTypes()[0];
        if (value != null) {
          if (ClassUtils.isAssignable(writeClass, value.getClass())) {
            try {
              if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
              }
              writeMethod.invoke(target, value);
            } catch (Throwable ex) {
              throw new FatalBeanException(
                "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
            }
          } else if ((ClassUtils.isPrimitiveWrapper(value.getClass()) || String.class.isAssignableFrom(value.getClass()))
            && (ClassUtils.isPrimitiveOrWrapper(writeClass) || writeClass == String.class)) {
            String sv = value.toString();
            if (writeClass.isPrimitive()) {
              writeClass = ClassUtils.resolvePrimitiveIfNecessary(writeClass);
            }

            try {
              if (String.class.isAssignableFrom(writeClass)) {
                writeMethod.invoke(target, sv);
              } else if (Integer.class.isAssignableFrom(writeClass)) {
                writeMethod.invoke(target, Integer.valueOf(sv));
              } else if (Float.class.isAssignableFrom(writeClass)) {
                writeMethod.invoke(target, Float.valueOf(sv));
              } else if (Double.class.isAssignableFrom(writeClass)) {
                writeMethod.invoke(target, Double.valueOf(sv));
              } else if (Character.class.isAssignableFrom(writeClass)) {
                writeMethod.invoke(target, sv.charAt(0));
              } else if (Short.class.isAssignableFrom(writeClass)) {
                writeMethod.invoke(target, Short.valueOf(sv));
              } else if (Long.class.isAssignableFrom(writeClass)) {
                writeMethod.invoke(target, Long.valueOf(sv));
              } else if (Byte.class.isAssignableFrom(writeClass)) {
                writeMethod.invoke(target, Byte.valueOf(sv));
              } else if (Boolean.class.isAssignableFrom(writeClass)) {
                writeMethod.invoke(target, Boolean.valueOf(sv));
              } else {
                throw new FatalBeanException(
                  "Could not copy property '" + targetPd.getName() + "' from source to target");
              }
            } catch (Throwable ex) {
              throw new FatalBeanException(
                "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
            }
          }
        }
      }
    }
  }
}
