/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.repository;

import cn.ibestcode.easiness.core.base.model.BaseJpaModel;
import cn.ibestcode.easiness.core.base.repository.BaseJpaRepository;
import cn.ibestcode.easiness.region.management.domain.RegionVo;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/17 20:13
 */
@NoRepositoryBean
public interface EasinessRegionBaseRepository<T extends BaseJpaModel> extends BaseJpaRepository<T> {

  T findByCode(String code);

  void deleteByCode(String code);

  List<RegionVo> findAllByCodeStartingWith(String code);

  List<RegionVo> findAllByLevelStartingWith(String level);

}
