/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.helper;

import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.model.PayStatus;
import cn.ibestcode.easiness.spring.utils.IpUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 21:13
 */
public class EasinessPayHelper {
  // region 在线支付
  public static EasinessPay getOnlineInstance(String orderUuid, String payType, int price,
                                             String payerUuid, long expirationAt, String currency) {
    if (StringUtils.isBlank(currency)) {
      currency = "CNY";
    }
    if (expirationAt < 0) {
      expirationAt = System.currentTimeMillis() + 5 * 60 * 1000;
    }
    EasinessPay pay = new EasinessPay();
    pay.setOffline(false);
    pay.setClientIp(IpUtil.getClientIpAddress());
    pay.setPayStatus(PayStatus.DURING);
    pay.setOrderUuid(orderUuid);
    pay.setPayType(payType);
    pay.setPrice(price);
    pay.setPayerUuid(payerUuid);
    pay.setExpirationAt(expirationAt);
    pay.setCurrency(currency);
    return pay;
  }


  public static EasinessPay getOnlineInstance(String orderUuid, String payType, int price,
                                          String payerUuid, long expirationAt) {
    return getOnlineInstance(orderUuid, payType, price, payerUuid, expirationAt, "CNY");
  }


  public static EasinessPay getOnlineInstance(String orderUuid, String payType, int price,
                                          String payerUuid) {
    return getOnlineInstance(orderUuid, payType, price, payerUuid,
      System.currentTimeMillis() + 5 * 60 * 1000);
  }
  // endregion

  // region 线下支付
  public static EasinessPay getOfflineInstance(String orderUuid, String payType, int price,
                                           String handlerUuid, String offlineDescription, String currency) {
    if (StringUtils.isBlank(currency)) {
      currency = "CNY";
    }
    EasinessPay pay = new EasinessPay();
    pay.setOffline(true);
    pay.setHandlerIp(IpUtil.getClientIpAddress());
    pay.setPayStatus(PayStatus.DURING);
    pay.setOrderUuid(orderUuid);
    pay.setPayType(payType);
    pay.setPrice(price);
    pay.setHandlerUuid(handlerUuid);
    pay.setOfflineDescription(offlineDescription);
    pay.setCurrency(currency);
    return pay;
  }

  public static EasinessPay getOfflineInstance(String orderUuid, String payType, int price,
                                           String handlerUuid, String offlineDescription) {
    return getOfflineInstance(orderUuid, payType, price, handlerUuid, offlineDescription, "CNY");
  }

  public static EasinessPay getOfflineInstance(String orderUuid, String payType, int price,
                                           String handlerUuid) {
    return getOfflineInstance(orderUuid, payType, price, handlerUuid, null);
  }


  public static EasinessPay getOfflineInstance(String orderUuid, String payType, int price) {
    return getOfflineInstance(orderUuid, payType, price, null);
  }

  // endregion
}
