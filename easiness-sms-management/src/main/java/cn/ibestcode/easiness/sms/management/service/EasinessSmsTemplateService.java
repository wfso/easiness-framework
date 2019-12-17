/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.service;

import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsTemplate;
import cn.ibestcode.easiness.sms.management.repository.EasinessSmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 23:31
 */
@Service
public class EasinessSmsTemplateService extends UuidBaseJpaService<EasinessSmsTemplate> {

  @Autowired
  private EasinessSmsTemplateRepository repository;

  @Override
  protected EasinessSmsTemplateRepository getRepository() {
    return repository;
  }

  public EasinessSmsTemplate getByTemplate(String template) {
    return repository.findByTemplate(template);
  }
}
