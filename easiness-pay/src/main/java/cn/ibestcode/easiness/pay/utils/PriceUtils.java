/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.utils;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 22:51
 */
public class PriceUtils {
  public static String transformPrice(int price) {
    String priceStr = String.valueOf(price);
    if (priceStr.length() > 2) {
      priceStr = priceStr.replaceAll("\\d{2}$", ".$0");
    } else {
      priceStr = "0." + (priceStr.length() < 2 ? "0" : "") + priceStr;
    }
    return priceStr;
  }
}
