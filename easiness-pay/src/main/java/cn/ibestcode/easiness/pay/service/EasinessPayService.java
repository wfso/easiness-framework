/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.service;

import cn.ibestcode.easiness.backup.entity.service.UuidBaseJpaBackupService;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.model.PayStatus;
import cn.ibestcode.easiness.pay.repository.EasinessPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 21:35
 */
@Service
public class EasinessPayService extends UuidBaseJpaBackupService<EasinessPay> {
  @Autowired
  private EasinessPayRepository repository;

  @Override
  protected EasinessPayRepository getRepository() {
    return repository;
  }


  public List<EasinessPay> getByOrderUuid(String orderUuid) {
    return repository.findAllByOrderUuid(orderUuid);
  }

  public List<EasinessPay> getByOrderUuidAndPayStatus(String orderUuid, PayStatus payStatus) {
    return repository.findAllByOrderUuidAndPayStatus(orderUuid, payStatus);
  }

  public List<EasinessPay> getByOrderUuidAndPayTypeAndPayStatus(String orderUuid, String payType, PayStatus payStatus) {
    return repository.findAllByOrderUuidAndPayTypeAndPayStatus(orderUuid, payType, payStatus);
  }

  public List<EasinessPay> getByPayerUuid(String payerUuid) {
    return repository.findAllByPayerUuid(payerUuid);
  }

  public List<EasinessPay> getByPayerUuidAndPayStatus(String payerUuid, PayStatus payStatus) {
    return repository.findAllByPayerUuidAndPayStatus(payerUuid, payStatus);
  }

  public List<EasinessPay> getByHandlerUuid(String handlerUuid) {
    return repository.findAllByHandlerUuid(handlerUuid);
  }

  public List<EasinessPay> getByHandlerUuidAndPayStatus(String handlerUuid, PayStatus payStatus) {
    return repository.findAllByHandlerUuidAndPayStatus(handlerUuid, payStatus);
  }

  public List<EasinessPay> getByPayStatusAndExpirationAtLessThan(PayStatus payStatus, long expirationAt) {
    return repository.findAllByPayStatusAndExpirationAtLessThan(payStatus, expirationAt);
  }
}
