package cn.ibestcode.easiness.spring.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.util.Map;

public class MultiValueMapUtil {
  public static MultiValueMap fromMap(Map map) {
    MultiValueMap multiValueMap = new LinkedMultiValueMap();
    multiValueMap.setAll(map);
    return multiValueMap;
  }

  public static MultiValueMap fromMonolayerObject(Object object) {
    final BeanWrapper sourceWrapper = new BeanWrapperImpl(object);
    PropertyDescriptor[] pds = sourceWrapper.getPropertyDescriptors();
    MultiValueMap multiValueMap = new LinkedMultiValueMap();
    for (PropertyDescriptor pd : pds) {
      Object propertyValue = sourceWrapper.getPropertyValue(pd.getName());
      if (propertyValue instanceof String) {
        if (!ObjectUtils.isEmpty(propertyValue)) {
          multiValueMap.add(pd.getName(), propertyValue);
        }
      }
    }
    return multiValueMap;
  }
}
