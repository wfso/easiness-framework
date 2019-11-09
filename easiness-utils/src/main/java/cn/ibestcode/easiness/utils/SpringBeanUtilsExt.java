package cn.ibestcode.easiness.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
