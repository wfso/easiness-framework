/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.processor;

import cn.ibestcode.easiness.eventbus.EventListener;
import cn.ibestcode.easiness.eventbus.annotation.Subscribe;
import cn.ibestcode.easiness.order.event.OrderStatusChangeEvent;
import cn.ibestcode.easiness.order.model.OrderStatus;
import cn.ibestcode.easiness.pay.biz.EasinessPayBiz;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/10 20:31
 */
@EventListener
public class EasinessOrderStatusChangeEventProcessor {
  @Autowired
  private EasinessPayBiz payBiz;

  @Subscribe
  public void orderStatusChangeEventProcess(OrderStatusChangeEvent event) {
    if (OrderStatus.PAID == event.getOrderStatus()) {
      payBiz.setPayStatusComplete(event.getPayUuid());
    }
  }
}
