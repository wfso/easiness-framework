/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.region.management.service;

import cn.ibestcode.easiness.region.management.model.EasinessNationality;
import cn.ibestcode.easiness.region.management.repository.EasinessNationalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/21 19:32
 */
@Service
public class EasinessNationalityService extends EasinessRegionBaseService<EasinessNationality> {
  @Autowired
  private EasinessNationalityRepository repository;

  public EasinessNationalityRepository getRepository(){
    return repository;
  }
}
