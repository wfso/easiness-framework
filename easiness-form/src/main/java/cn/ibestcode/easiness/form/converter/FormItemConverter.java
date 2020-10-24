/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.converter;

import cn.ibestcode.easiness.form.domain.Item;
import cn.ibestcode.easiness.form.model.FormData;
import cn.ibestcode.easiness.form.model.FormItem;
import cn.ibestcode.easiness.utils.MapUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:48
 */
@Slf4j
public class FormItemConverter {
  private static volatile Map<String, ItemConverterProvider> providerMap;
  private static Map<String, Class> classMap = new HashMap<>();
  private static Map<String, Item> defaultItemMap = new HashMap<>();

  private static synchronized <T extends Item> void init() {
    if (providerMap == null) {
      Map<String, ItemConverterProvider> map = new HashMap<>();
      ServiceLoader<ItemConverterProvider> providerServiceLoader = ServiceLoader.load(ItemConverterProvider.class);
      for (ItemConverterProvider provider : providerServiceLoader) {
        Class<T> cl = getConverterActualType(provider);
        if (map.containsKey(cl)) {
          log.warn("针对同一个 {} 提供了两个 ItemConverterProvider", cl.getName());
        }
        String simpleName = cl.getSimpleName();
        map.put(simpleName, provider);
        classMap.put(simpleName, cl);
        try {
          defaultItemMap.put(simpleName, cl.newInstance());
        } catch (Exception e) {
          log.warn(e.getMessage(), e);
        }
      }
      providerMap = map;
    }
  }

  private static <T extends Item> Class<T> getConverterActualType(ItemConverterProvider<T> provider) {
    Type type = provider.getClass().getGenericSuperclass();
    if (type instanceof ParameterizedType) {
      Type[] types = ((ParameterizedType) type).getActualTypeArguments();
      if (types.length == 1) {
        return (Class<T>) types[0];
      } else {
        throw new IllegalArgumentException("实现类只允许有一个泛型参数");
      }
    }
    Type[] types = provider.getClass().getGenericInterfaces();
    for (Type t : types) {
      if (t instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) t;
        if (parameterizedType.getRawType() == ItemConverterProvider.class) {
          return (Class<T>) parameterizedType.getActualTypeArguments()[0];
        }
      }
    }
    throw new IllegalArgumentException(provider.getClass().getName() + " is not a ItemConverterProvider");
  }


  private static <T extends Item> ItemConverterProvider<T> getProviderByType(String type) {
    if (providerMap == null) {
      init();
    }
    return (ItemConverterProvider<T>) providerMap.get(type);
  }

  public static Map<String, Item> getDefaultItemMap() {
    if (providerMap == null) {
      init();
    }
    return defaultItemMap;
  }

  public static <T extends Item> Class<T> getClassBySimpleName(String simpleName) {
    if (providerMap == null) {
      init();
    }
    return (Class<T>) classMap.get(simpleName);
  }

  public static <T extends Item> T to(FormItem item) {
    return ((ItemConverterProvider<T>) getProviderByType(item.getType())).to(item);
  }

  public static <T extends Item> T to(FormItem item, FormData data) {
    return ((ItemConverterProvider<T>) getProviderByType(item.getType())).to(item, data);
  }

  public static <T extends Item> FormItem from(T t) {
    return getProviderByType(t.getType()).from(t);
  }

  public static <T extends Item> T to(Map<String, String> map) {
    if (map.containsKey("type")) {
      T item = getItemBySimpleName(map.get("type"));
      try {
        MapUtil.mapToObject(map,item);
      } catch (Exception e) {
        log.warn(e.getMessage(), e);
      }
      return item;
    }
    return null;
  }

  public static <T extends Item> T getItemBySimpleName(String simpleName) {
    Class<T> clazz = getClassBySimpleName(simpleName);
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
    }
    return null;
  }

}
