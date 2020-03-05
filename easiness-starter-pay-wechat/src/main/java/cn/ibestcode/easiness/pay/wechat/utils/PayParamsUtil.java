/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.pay.wechat.utils;

import cn.ibestcode.easiness.utils.MapUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/3/4 22:10
 */
public class PayParamsUtil {
  public static String genXmlParams(Object object) {
    TreeMap<String, String> treeMap = MapUtil.monolayerObjectToTreeMap(object);
    StringBuilder sb = new StringBuilder();
    sb.append("<xml>");
    for (Map.Entry<String, String> entry : treeMap.entrySet()) {
      String tag = entry.getKey().replaceAll("([^\\_])([A-Z])", "$1_$2").toLowerCase();
      sb.append("<").append(tag).append(">").append(entry.getValue())
        .append("</").append(tag).append(">");
    }
    sb.append("</xml>");
    return sb.toString();
  }
}
