/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.service;

import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsCrontab;
import cn.ibestcode.easiness.sms.management.repository.EasinessSmsCrontabRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 23:17
 */
public class EasinessSmsCrontabService extends UuidBaseJpaService<EasinessSmsCrontab> {

  @Autowired
  private EasinessSmsCrontabRepository repository;

  protected EasinessSmsCrontabRepository getRepository() {
    return repository;
  }
}
