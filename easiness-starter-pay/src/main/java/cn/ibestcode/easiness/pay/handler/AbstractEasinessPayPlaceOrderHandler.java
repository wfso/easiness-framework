/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.handler;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.order.service.EasinessOrderService;
import cn.ibestcode.easiness.pay.EasinessPayConstant;
import cn.ibestcode.easiness.pay.biz.EasinessPayBiz;
import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.domain.PlaceOrderResult;
import cn.ibestcode.easiness.pay.helper.EasinessPayHelper;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.model.PayStatus;
import cn.ibestcode.easiness.pay.properties.EasinessPayProperties;
import cn.ibestcode.easiness.pay.service.EasinessPayService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/10 20:10
 */
public abstract class AbstractEasinessPayPlaceOrderHandler implements EasinessPayPlaceOrderHandler {
  @Autowired
  protected EasinessPayService easinessPayService;
  @Autowired
  protected EasinessPayBiz easinessPayBiz;
  @Autowired
  protected EasinessOrderService easinessOrderService;
  @Autowired
  protected EasinessConfiguration configuration;
  @Autowired
  protected EasinessPayProperties payProperties;


  @Override
  public PlaceOrderResult placeOrderHandle(String orderUuid, String payerUuid, Map<String, String> params) {
    EasinessOrder easinessOrder = easinessOrderService.getByUuid(orderUuid);
    EasinessPay easinessPay = genEasinessPay(easinessOrder, payerUuid, params);
    PlaceOrderResult result = placeOrder(easinessOrder, easinessPay, easinessPayBiz.getPassbackParams(), params);
    return result;
  }

  protected abstract PlaceOrderResult placeOrder(EasinessOrder order, EasinessPay pay, EasinessPayPassbackParams passbackParams, Map<String, String> params);

  protected EasinessPay genEasinessPay(EasinessOrder order, String payerUuid, Map<String, String> param) {

    // 查询是否已经有未支付的订单
    List<EasinessPay> easinessPays = easinessPayService.getByOrderUuidAndPayTypeAndPayStatus(order.getUuid(), supportType(), PayStatus.DURING);

    // 订单的过期时间少于半小时，则自动设置过期
    long expiration = System.currentTimeMillis() + 30 * 60 * 1000;

    EasinessPay easinessPay = null;

    // 如果有支付中的订单，则获取并使用
    for (EasinessPay pay : easinessPays) {
      if (easinessPay == null && pay.getExpirationAt() > expiration) {
        easinessPay = pay;
      } else {
        // 多余的订单 和 过期时间少于半小时的订单，则自动设置过期
        easinessPayBiz.setPayStatusTimeout(pay.getUuid());
      }
    }

    // 如果没有支付中的订单，则创建并使用
    if (easinessPay == null) {
      easinessPay = EasinessPayHelper.getOnlineInstace(order.getUuid(), supportType(), order.getPayablePrice(), payerUuid);
      // 订单默认过期时间为2小时
      long expire = payProperties.getExpire();
      if (expire == 0) {
        expire = 2 * 60 * 60 * 1000;
      }
      expire = configuration.getLongConfig(EasinessPayConstant.PAY_DEFAULT_EXPIRE, expire);
      easinessPay.setExpirationAt(System.currentTimeMillis() + expire);
      easinessPayBiz.create(easinessPay);
    }

    return easinessPay;
  }
}
