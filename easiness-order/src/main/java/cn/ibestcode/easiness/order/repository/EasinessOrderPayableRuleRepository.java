/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.repository;

import cn.ibestcode.easiness.core.base.repository.UuidBaseJpaRepository;
import cn.ibestcode.easiness.order.model.EasinessOrderPayableRule;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
public interface EasinessOrderPayableRuleRepository extends UuidBaseJpaRepository<EasinessOrderPayableRule> {
  List<EasinessOrderPayableRule> findAllByOrderUuidOrderByWeightAsc(String orderUuid);

  EasinessOrderPayableRule findByOrderUuidAndPayableType(String orderUuid, String payableType);
}
