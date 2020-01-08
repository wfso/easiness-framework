/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.handler;

import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.order.model.EasinessOrderPayableRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Component
public class DefaultOrderPayableRuleHandler {

  @Autowired
  private List<IOrderPayableRuleHandler> handlers;

  private Map<String, IOrderPayableRuleHandler> handlerMap = new HashMap<>();

  public boolean handle(EasinessOrder order, EasinessOrderPayableRule rule) {
    if (getHandlerMap().containsKey(rule.getPayableType())) {
      return getHandlerMap().get(rule.getPayableType()).handle(order, rule);
    }
    return false;
  }

  protected Map<String, IOrderPayableRuleHandler> getHandlerMap() {
    if (handlerMap == null) {
      synchronized (this) {
        if (handlerMap == null) {
          for (IOrderPayableRuleHandler handler : handlers) {
            if (handlerMap.containsKey(handler.supportType())) {
              throw new RuntimeException(
                new StringBuilder()
                  .append("The IOrderPayableRuleHandler that type is ")
                  .append(handler.supportType())
                  .append(" Existed")
                  .toString()
              );
            }
            handlerMap.put(handler.supportType(), handler);
          }
        }
      }
    }
    return handlerMap;
  }
}
