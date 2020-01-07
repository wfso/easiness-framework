/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.service;

import cn.ibestcode.easiness.backup.entity.service.UuidBaseJpaBackupService;
import cn.ibestcode.easiness.order.model.EasinessOrderItem;
import cn.ibestcode.easiness.order.model.OrderStatus;
import cn.ibestcode.easiness.order.repository.EasinessOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Service
public class EasinessOrderItemService extends UuidBaseJpaBackupService<EasinessOrderItem> {
  @Autowired
  private EasinessOrderItemRepository repository;

  @Override
  protected EasinessOrderItemRepository getRepository() {
    return repository;
  }

  public List<EasinessOrderItem> getByOwnerUuid(String ownerUuid) {
    return repository.findAllByOwnerUuid(ownerUuid);
  }

  public List<EasinessOrderItem> getByOwnerUuidAndOrderStatus(String ownerUuid, OrderStatus orderStatus) {
    return repository.findAllByOwnerUuidAndOrderStatus(ownerUuid, orderStatus);
  }

  public List<EasinessOrderItem> getByOrderUuid(String orderUuid) {
    return repository.findAllByOrderUuid(orderUuid);
  }

  public List<EasinessOrderItem> getByObjectUuid(String objectUuid) {
    return repository.findAllByObjectUuid(objectUuid);
  }
}
