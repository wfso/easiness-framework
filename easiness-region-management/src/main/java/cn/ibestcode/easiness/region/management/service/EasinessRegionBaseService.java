/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.service;

import cn.ibestcode.easiness.core.base.model.BaseJpaModel;
import cn.ibestcode.easiness.core.base.service.BaseJpaService;
import cn.ibestcode.easiness.region.management.domain.RegionVo;
import cn.ibestcode.easiness.region.management.repository.EasinessRegionBaseRepository;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/21 19:26
 */
public abstract class EasinessRegionBaseService<T extends BaseJpaModel> extends BaseJpaService<T> {

  @Override
  protected abstract EasinessRegionBaseRepository<T> getRepository();

  public T getByCode(String code) {
    return getRepository().findByCode(code);
  }

  public T removeByCode(String code) {
    T entity = getRepository().findByCode(code);
    getRepository().deleteByCode(code);
    return entity;
  }

  public List<RegionVo> getByCodeStartingWith(String code) {
    return getRepository().findAllByCodeStartingWith(code);
  }

  public List<RegionVo> getByLevelStartingWith(String level) {
    return getRepository().findAllByLevelStartingWith(level);
  }
}
