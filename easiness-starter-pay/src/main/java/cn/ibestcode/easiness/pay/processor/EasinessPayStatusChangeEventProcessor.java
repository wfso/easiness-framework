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
import cn.ibestcode.easiness.order.biz.EasinessOrderBiz;
import cn.ibestcode.easiness.pay.event.PayStatusChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/10 20:35
 */
@EventListener
public class EasinessPayStatusChangeEventProcessor {
  @Autowired
  private EasinessOrderBiz orderBiz;

  @Subscribe
  public void payStatusChangeEventProcess(PayStatusChangeEvent event) {
    switch (event.getPayStatus()) {
      case PAID: {
        orderBiz.setOrderStatusPaid(event.getOrderUuid(), event.getPayUuid());
        break;
      }
      case DURING: {
        orderBiz.setOrderStatusDuring(event.getOrderUuid(), event.getPayUuid());
        break;
      }
      case CANCEL: {
        orderBiz.setOrderStatusUnpaid(event.getOrderUuid(), event.getPayUuid());
        break;
      }
      case TIMEOUT: {
        orderBiz.setOrderStatusUnpaid(event.getOrderUuid(), event.getPayUuid());
        break;
      }
      case COMPLETE: {
        orderBiz.setOrderStatusComplete(event.getOrderUuid());
        break;
      }
    }

  }
}
