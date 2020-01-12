/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.handler;

import cn.ibestcode.easiness.pay.domain.PlaceOrderResult;
import cn.ibestcode.easiness.pay.exception.EasinessPayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 21:23
 */
@Component
public class EasinessDefaultPayHandlerBus implements EasinessPayNotifyHandlerBus, EasinessPayPlaceOrderHandlerBus {

  @Autowired
  private List<EasinessPayNotifyHandler> notifyHandlers;

  @Autowired
  private List<EasinessPayPlaceOrderHandler> placeOrderHandlers;

  private Map<String, EasinessPayNotifyHandler> notifyHandlerMap;

  private Map<String, EasinessPayPlaceOrderHandler> placeOrderHandlerMap;

  @Override
  public String notifyHandle(String payType, String payUuid) {
    Map<String, EasinessPayNotifyHandler> handlerMap = getNotifyHandlerMap();
    if (handlerMap.containsKey(payType)) {
      return handlerMap.get(payType).notifyHandle(payUuid);
    }
    throw new EasinessPayException("The EasinessPayNotifyHandler Not Exist By Type : " + payType);
  }

  @Override
  public PlaceOrderResult placeOrderHandle(String orderUuid, String payType, String payerUuid, Map<String, String> params) {
    Map<String, EasinessPayPlaceOrderHandler> handlerMap = getPlaceOrderHandlerMap();
    if (handlerMap.containsKey(payType)) {
      return handlerMap.get(payType).placeOrderHandle(orderUuid, payerUuid, params);
    }
    throw new EasinessPayException("The EasinessPayPlaceOrderHandler Not Exist By Type : " + payType);
  }


  protected Map<String, EasinessPayNotifyHandler> getNotifyHandlerMap() {
    if (notifyHandlerMap == null) {
      synchronized (this) {
        if (notifyHandlerMap == null) {
          notifyHandlerMap = new HashMap<>();
          for (EasinessPayNotifyHandler handler : notifyHandlers) {
            notifyHandlerMap.put(handler.supportType(), handler);
          }
        }
      }
    }
    return notifyHandlerMap;
  }

  protected Map<String, EasinessPayPlaceOrderHandler> getPlaceOrderHandlerMap() {
    if (placeOrderHandlerMap == null) {
      synchronized (this) {
        if (placeOrderHandlerMap == null) {
          placeOrderHandlerMap = new HashMap<>();
          for (EasinessPayPlaceOrderHandler handler : placeOrderHandlers) {
            placeOrderHandlerMap.put(handler.supportType(), handler);
          }
        }
      }
    }
    return placeOrderHandlerMap;
  }
}
