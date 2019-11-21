/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.eventbus;

import cn.ibestcode.easiness.eventbus.annotation.Listener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/25 19:56
 */
@Component
public class EasinessEventBusBeanPostProcessor implements BeanPostProcessor {

  private List<Object> Listeners = new ArrayList<>();

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (AnnotationUtils.findAnnotation(bean.getClass(), Listener.class) != null) {
      Listeners.add(beanName);
    }
    return bean;
  }

  public void init(EventBus eventBus) {
    for (Object listener : Listeners) {
      eventBus.register(listener);
    }
    Listeners.clear();
  }

}
