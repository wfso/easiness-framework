/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.repository;

import cn.ibestcode.easiness.core.base.repository.UuidBaseJpaRepository;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.model.PayStatus;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 20:28
 */
public interface EasinessPayRepository extends UuidBaseJpaRepository<EasinessPay> {
  List<EasinessPay> findAllByOrderUuid(String orderUuid);

  List<EasinessPay> findAllByOrderUuidAndPayStatus(String orderUuid, PayStatus payStatus);

  List<EasinessPay> findAllByOrderUuidAndPayTypeAndPayStatus(String orderUuid, String payType, PayStatus payStatus);

  List<EasinessPay> findAllByPayerUuid(String payerUuid);

  List<EasinessPay> findAllByPayerUuidAndPayStatus(String payerUuid, PayStatus payStatus);

  List<EasinessPay> findAllByHandlerUuid(String handlerUuid);

  List<EasinessPay> findAllByHandlerUuidAndPayStatus(String handlerUuid, PayStatus payStatus);

  List<EasinessPay> findAllByPayStatusAndExpirationAtLessThan(PayStatus payStatus, long expirationAt);
}
