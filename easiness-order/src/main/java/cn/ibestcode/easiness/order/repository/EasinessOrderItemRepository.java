/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.repository;

import cn.ibestcode.easiness.core.base.repository.UuidBaseJpaRepository;
import cn.ibestcode.easiness.order.model.EasinessOrderItem;
import cn.ibestcode.easiness.order.model.OrderStatus;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
public interface EasinessOrderItemRepository extends UuidBaseJpaRepository<EasinessOrderItem> {
  List<EasinessOrderItem> findAllByOwnerUuid(String ownerUuid);

  List<EasinessOrderItem> findAllByOwnerUuidAndOrderStatus(String ownerUuid, OrderStatus orderStatus);

  List<EasinessOrderItem> findAllByOrderUuid(String orderUuid);

  List<EasinessOrderItem> findAllByObjectUuid(String objectUuid);
}
