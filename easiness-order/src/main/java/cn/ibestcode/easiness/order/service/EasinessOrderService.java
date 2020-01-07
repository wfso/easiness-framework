/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.service;

import cn.ibestcode.easiness.backup.entity.service.UuidBaseJpaBackupService;
import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.order.model.OrderStatus;
import cn.ibestcode.easiness.order.repository.EasinessOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Service
public class EasinessOrderService extends UuidBaseJpaBackupService<EasinessOrder> {
  @Autowired
  private EasinessOrderRepository repository;

  @Override
  protected EasinessOrderRepository getRepository() {
    return repository;
  }

  public EasinessOrder getByPayUuid(String payUuid) {
    return repository.findByPayUuid(payUuid);
  }

  public List<EasinessOrder> getByOwnerUuid(String ownerUuid) {
    return repository.findAllByOwnerUuid(ownerUuid);
  }

  public List<EasinessOrder> getByOwnerUuidAndOrderStatus(String ownerUuid, OrderStatus orderStatus) {
    return repository.findAllByOwnerUuidAndOrderStatus(ownerUuid, orderStatus);
  }
}
