/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.converter;

import cn.ibestcode.easiness.form.domain.DefaultForm;
import cn.ibestcode.easiness.form.domain.DefaultItem;
import cn.ibestcode.easiness.form.domain.Limit;
import cn.ibestcode.easiness.form.model.FormData;
import cn.ibestcode.easiness.form.model.FormItem;
import cn.ibestcode.easiness.form.model.FormPattern;
import cn.ibestcode.easiness.utils.MapUtil;
import cn.ibestcode.easiness.utils.SpringBeanUtilsExt;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:48
 */
@Slf4j
public class FormConverter {
  private static Map<String, Class> classMap = null;
  private static final Map<String, Limit> defaultLimitMap = new HashMap<>();

  private static synchronized <T extends Limit> void init() {
    if (classMap == null) {
      Map<String, Class> map = new HashMap<>();
      ServiceLoader<Limit> itemServiceLoader = ServiceLoader.load(Limit.class);
      for (Limit item : itemServiceLoader) {
        Class<T> cl = (Class<T>) item.getClass();
        String simpleName = cl.getSimpleName();
        map.put(simpleName, cl);
        try {
          defaultLimitMap.put(simpleName, cl.newInstance());
        } catch (Exception e) {
          log.warn(e.getMessage(), e);
        }
      }
      classMap = map;
    }
  }


  public static Map<String, Limit> getAllLimitMap() {
    if (classMap == null) {
      init();
    }
    return defaultLimitMap;
  }

  public static <T extends Limit> Class<T> getClassBySimpleName(String simpleName) {
    if (classMap == null) {
      init();
    }
    return (Class<T>) classMap.get(simpleName);
  }

  public static DefaultForm convert(FormPattern formPattern) {
    DefaultForm defaultForm = new DefaultForm();
    SpringBeanUtilsExt.copyProperties(formPattern, defaultForm, "items");
    for (FormItem item : formPattern.getItems()) {
      defaultForm.addItem(convert(item));
    }
    return defaultForm;
  }

  public static FormPattern convert(DefaultForm defaultForm) {
    FormPattern formPattern = new FormPattern();
    SpringBeanUtilsExt.copyProperties(defaultForm, formPattern, "items");
    for (DefaultItem item : defaultForm.getItems()) {
      formPattern.addItem(convert(item));
    }
    return formPattern;
  }

  public static DefaultItem convert(FormItem item) {
    DefaultItem defaultItem = new DefaultItem();
    SpringBeanUtilsExt.copyProperties(item, defaultItem);
    defaultItem.setLimit(genLimit(item.getType(), item.getLimit()));
    return defaultItem;
  }

  public static DefaultItem convert(FormItem item, FormData data) {
    DefaultItem defaultItem = new DefaultItem();
    SpringBeanUtilsExt.copyProperties(item, defaultItem);
    defaultItem.setLimit(genLimit(item.getType(), item.getLimit()));
    defaultItem.setValue(data.getData().get(item.getName()));
    return defaultItem;
  }

  public static FormItem convert(DefaultItem defaultItem) {
    FormItem item = new FormItem();
    SpringBeanUtilsExt.copyProperties(defaultItem, item);
    item.setLimit(MapUtil.monolayerObjectToMap(defaultItem.getLimit()));
    return item;
  }

  public static Limit genLimit(String type, Map<String, String> map) {
    Limit limit = genLimitBySimpleName(type);
    if (limit != null) {
      try {
        MapUtil.mapToObject(map, limit);
      } catch (Exception e) {
        log.warn(e.getMessage(), e);
      }
    }
    return limit;
  }

  public static <T extends Limit> Limit genLimitBySimpleName(String simpleName) {
    Class<T> clazz = getClassBySimpleName(simpleName);
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
    }
    return null;
  }

}
