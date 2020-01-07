/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.service;

import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.order.model.EasinessOrderExtend;
import cn.ibestcode.easiness.order.repository.EasinessOrderExtendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Service
public class EasinessOrderExtendService extends UuidBaseJpaService<EasinessOrderExtend> {
  @Autowired
  private EasinessOrderExtendRepository repository;

  @Override
  protected EasinessOrderExtendRepository getRepository() {
    return repository;
  }

  public List<EasinessOrderExtend> getByOrderUuid(String orderUuid) {
    return repository.findAllByOrderUuid(orderUuid);
  }

  public EasinessOrderExtend getByOrderUuidAndExtendKey(String orderUuid, String key) {
    return repository.findAllByOrderUuidAndExtendKey(orderUuid, key);
  }
}
