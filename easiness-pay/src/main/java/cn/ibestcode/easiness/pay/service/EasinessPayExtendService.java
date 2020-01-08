/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.service;

import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.pay.model.EasinessPayExtend;
import cn.ibestcode.easiness.pay.repository.EasinessPayExtendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 21:15
 */
@Service
public class EasinessPayExtendService extends UuidBaseJpaService<EasinessPayExtend> {
  @Autowired
  private EasinessPayExtendRepository repository;

  @Override
  protected EasinessPayExtendRepository getRepository() {
    return repository;
  }


  public List<EasinessPayExtend> getByPayUuid(String payUuid) {
    return repository.findAllByPayUuid(payUuid);
  }

  public EasinessPayExtend getByPayUuidAndExtendKey(String payUuid, String key) {
    return repository.findAllByPayUuidAndExtendKey(payUuid, key);
  }
}
