/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.filter;

import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.annotation.PostConstruct;
import javax.servlet.DispatcherType;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
public class SpringShiroFilterRegistrationBean extends FilterRegistrationBean<AbstractShiroFilter> {
  @Autowired
  private AbstractShiroFilter shiroFilter;

  @PostConstruct
  public void init() {
    setFilter(shiroFilter);
    setDispatcherTypes(
      DispatcherType.FORWARD,
      DispatcherType.INCLUDE,
      DispatcherType.REQUEST,
      DispatcherType.ASYNC,
      DispatcherType.ERROR
    );
  }
}
