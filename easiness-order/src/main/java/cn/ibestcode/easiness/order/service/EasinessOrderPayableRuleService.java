/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.service;

import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.order.model.EasinessOrderPayableRule;
import cn.ibestcode.easiness.order.repository.EasinessOrderPayableRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Service
public class EasinessOrderPayableRuleService extends UuidBaseJpaService<EasinessOrderPayableRule> {
  @Autowired
  private EasinessOrderPayableRuleRepository repository;

  @Override
  protected EasinessOrderPayableRuleRepository getRepository() {
    return repository;
  }

  public List<EasinessOrderPayableRule> getByOrderUuid(String orderUuid) {
    return repository.findAllByOrderUuidOrderByWeightAsc(orderUuid);
  }

  public EasinessOrderPayableRule getByOrderUuidAndType(String orderUuid, String type) {
    return repository.findByOrderUuidAndPayableType(orderUuid, type);
  }
}
