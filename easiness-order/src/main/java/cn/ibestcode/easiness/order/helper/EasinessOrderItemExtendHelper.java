/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.helper;

import cn.ibestcode.easiness.order.model.EasinessOrderItemExtend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
public class EasinessOrderItemExtendHelper {


  /**
   * 用于生成 EasinessOrderExtend 对象的 工具函数
   *
   * @param key     键（给程序看的，标识符）
   * @param keyName 键名（给人看的，一般为有含义的中文词组或短语）
   * @param value   值
   * @return EasinessOrderExtend 对象
   */
  public static EasinessOrderItemExtend getInstance(String key, String keyName, String value) {
    EasinessOrderItemExtend orderItemExtend = new EasinessOrderItemExtend();
    orderItemExtend.setExtendKey(key);
    orderItemExtend.setKeyName(keyName);
    orderItemExtend.setValue(value);
    return orderItemExtend;
  }

  /**
   * 用于生成 EasinessOrderExtend 对象的 工具函数
   *
   * @param keyName 键名（给人看的，一般为有含义的中文词组或短语）
   * @param value   值
   * @return EasinessOrderExtend 对象
   */
  public static EasinessOrderItemExtend getInstance(String keyName, String value) {
    return getInstance(keyName, keyName, value);
  }

  /**
   * 用于生成 EasinessOrderExtend 对象列表的 工具函数
   *
   * @param map 包含 extendKey-value 对的 Map 对象
   * @return EasinessOrderExtend 对象的列表
   */
  public static List<EasinessOrderItemExtend> getInstanceList(Map<String, String> map) {
    List<EasinessOrderItemExtend> extendList = new ArrayList<>();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      extendList.add(getInstance(entry.getKey(), entry.getValue()));
    }
    return extendList;
  }
}
