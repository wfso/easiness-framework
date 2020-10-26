/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.service;

import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.form.model.FormPattern;
import cn.ibestcode.easiness.form.repository.FormPatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/26 9:46
 */
@Service
public class FormPatternService extends UuidBaseJpaService<FormPattern> {
  @Autowired
  private FormPatternRepository repository;

  @Override
  protected FormPatternRepository getRepository() {
    return repository;
  }
}
