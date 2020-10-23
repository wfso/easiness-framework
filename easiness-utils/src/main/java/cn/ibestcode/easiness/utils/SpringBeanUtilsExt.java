package cn.ibestcode.easiness.utils;

import org.springframework.beans.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class SpringBeanUtilsExt extends BeanUtils {

  private static List<String> defaultIgnoreProperties = Arrays.asList("available");

  public static void setDefaultIgnoreProperties(String... ignoreProperties) {
    defaultIgnoreProperties = Arrays.asList(ignoreProperties);
  }

  // 获取值为null的属性名
  public static Set<String> getNullPropertyNames(Object source) {
    final BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = sourceWrapper.getPropertyDescriptors();

    Set<String> noValuePropertySet = new HashSet<>();
    for (PropertyDescriptor pd : pds) {
      if (!defaultIgnoreProperties.contains(pd.getName())) {
        Object propertyValue = sourceWrapper.getPropertyValue(pd.getName());
        if (propertyValue == null) noValuePropertySet.add(pd.getName());
      }
    }
    noValuePropertySet.addAll(defaultIgnoreProperties);
    return noValuePropertySet;
  }

  // 获取值为“空”（null，空字符串，空集合等）的属性名
  public static Set<String> getEmptyPropertyNames(Object source) {
    final BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = sourceWrapper.getPropertyDescriptors();

    Set<String> noValuePropertySet = new HashSet<>();
    for (PropertyDescriptor pd : pds) {
      if (!defaultIgnoreProperties.contains(pd.getName())) {
        Object propertyValue = sourceWrapper.getPropertyValue(pd.getName());
        if (ObjectUtils.isEmpty(propertyValue)) noValuePropertySet.add(pd.getName());
      }
    }
    noValuePropertySet.addAll(defaultIgnoreProperties);
    return noValuePropertySet;
  }

  // 忽略 null 进行 copy
  public static void copyPropertiesIgnoreNull(Object source, Object target) throws BeansException {
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source).toArray(new String[0]));
  }


  // 忽略 “空”（null，空字符串，空集合等） 进行 copy
  public static void copyPropertiesIgnoreEmpty(Object source, Object target) throws BeansException {
    BeanUtils.copyProperties(source, target, getEmptyPropertyNames(source).toArray(new String[0]));
  }

  // 忽略 null 进行 copy
  public static void copyPropertiesIgnoreNull(Object source, Object target, String... ignoreProperties) throws BeansException {
    Set<String> noValuePropertySet = getNullPropertyNames(source);
    noValuePropertySet.addAll(Arrays.asList(ignoreProperties));
    BeanUtils.copyProperties(source, target, noValuePropertySet.toArray(new String[0]));
  }


  // 忽略 “空”（null，空字符串，空集合等） 进行 copy
  public static void copyPropertiesIgnoreEmpty(Object source, Object target, String... ignoreProperties) throws BeansException {
    Set<String> noValuePropertySet = getNullPropertyNames(source);
    noValuePropertySet.addAll(Arrays.asList(ignoreProperties));
    BeanUtils.copyProperties(source, target, noValuePropertySet.toArray(new String[0]));
  }


  private static void copyProperties(Map source, Object target, @Nullable Class<?> editable,
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
    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
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
          } else if ((ClassUtils.isPrimitiveWrapper(value.getClass()) || value.getClass() == String.class)
            && (ClassUtils.isPrimitiveOrWrapper(writeClass) || writeClass == String.class)) {
            String sv = value.toString();
            if (writeClass.isPrimitive()) {
              writeClass = ClassUtils.resolvePrimitiveIfNecessary(writeClass);
            }

            try {
              if (Integer.class.isAssignableFrom(writeClass)) {
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
