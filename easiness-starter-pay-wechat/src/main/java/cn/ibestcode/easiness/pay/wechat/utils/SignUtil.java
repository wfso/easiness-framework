/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.wechat.utils;

import cn.ibestcode.easiness.utils.DigestUtil;
import cn.ibestcode.easiness.utils.MacUtil;
import cn.ibestcode.easiness.utils.MapUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/28 18:52
 */
public class SignUtil {
  public static String sign(Map<String, String> params, String key, String signType) {
    params.remove("sign");
    if (!(params instanceof TreeMap)) {
      params = new TreeMap<>(params);
    }
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      if (entry.getValue() != null) {
        sb.append(entry.getKey())
          .append("=")
          .append(entry.getValue())
          .append("&");
      }
    }
    sb.append("key=").append(key);
    return sign(sb.toString(), key, signType);
  }

  public static String sign(String str, String key, String signType) {
    switch (signType.toLowerCase()) {
      case "md5": {
        return DigestUtil.md5Hex(str);
      }
      case "hmac-sha256":
      case "hmacsha256": {
        return MacUtil.hmacSha256Hex(str, key);
      }
    }
    return null;
  }


  public static String sign(Object object, String appKey, String signType) {
    TreeMap<String, String> treeMap = MapUtil.monolayerObjectToTreeMap(object);
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : treeMap.entrySet()) {
      if (!entry.getKey().equalsIgnoreCase("sign") && StringUtils.isNotBlank(entry.getValue())) {
        sb.append(entry.getKey().replaceAll("([^\\_])([A-Z])", "$1_$2").toLowerCase().replace(".", "`.`"))
          .append("=")
          .append(entry.getValue())
          .append("&");
      }
    }
    sb.append("key").append("=").append(appKey);
    return SignUtil.sign(sb.toString(), appKey, signType);
  }
}
