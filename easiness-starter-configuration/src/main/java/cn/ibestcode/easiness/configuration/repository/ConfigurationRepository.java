/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.configuration.repository;


import cn.ibestcode.easiness.configuration.model.Configuration;
import cn.ibestcode.easiness.core.base.repository.BaseJpaRepository;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
public interface ConfigurationRepository extends BaseJpaRepository<Configuration> {
  Configuration findByConfigKey(String configKey);

  List<Configuration> findByConfigKeyStartingWith(String configKey);
}
