/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.service;

import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.form.model.FormItem;
import cn.ibestcode.easiness.form.repository.FormItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/26 9:46
 */
public class FormItemService extends UuidBaseJpaService<FormItem> {
  @Autowired
  private FormItemRepository repository;

  @Override
  protected FormItemRepository getRepository() {
    return repository;
  }
}
