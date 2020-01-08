/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.repository;

import cn.ibestcode.easiness.core.base.repository.UuidBaseJpaRepository;
import cn.ibestcode.easiness.pay.model.EasinessPayExtend;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 20:25
 */
public interface EasinessPayExtendRepository extends UuidBaseJpaRepository<EasinessPayExtend> {
  List<EasinessPayExtend> findAllByPayUuid(String payUuid);

  EasinessPayExtend findAllByPayUuidAndExtendKey(String payUuid, String key);
}
