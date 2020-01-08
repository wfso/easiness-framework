/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.handler;

import cn.ibestcode.easiness.pay.domain.PlaceOrderResult;

import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 21:33
 */
public interface EasinessPayPlaceOrderHandlerBus {
  PlaceOrderResult placeOrderHandle(String orderUuid, String payType, String payerUuid, Map<String, String> params);
}
