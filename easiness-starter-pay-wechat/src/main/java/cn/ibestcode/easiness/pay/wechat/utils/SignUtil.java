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
    StringBuffer buffer = new StringBuffer();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      if (entry.getValue() != null) {
        buffer.append(entry.getKey())
          .append("=")
          .append(entry.getValue())
          .append("&");
      }
    }
    buffer.append("key=").append(key);
    switch (signType.toLowerCase()) {
      case "md5": {
        return DigestUtil.md5Hex(buffer.toString());
      }
      case "hmac-sha256":
      case "hmacsha256": {
        return MacUtil.hmacSha256Hex(buffer.toString(), key);
      }
    }
    return null;
  }
}
