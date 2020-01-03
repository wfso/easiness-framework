/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.service;

import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.file.management.model.EasinessStorageFile;
import cn.ibestcode.easiness.file.management.repository.EasinessStorageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
@Service
public class EasinessStorageFileService extends UuidBaseJpaService<EasinessStorageFile> {
  @Autowired
  private EasinessStorageFileRepository repository;

  public EasinessStorageFileRepository getRepository() {
    return repository;
  }

  public EasinessStorageFile getBySha256AndSize(String sha256, long size) {
    return repository.findBySha256AndSize(sha256, size);
  }

  public EasinessStorageFile getByUrl(String url) {
    return repository.findByUrl(url);
  }

  public boolean existsBySha256AndSize(String sha256, long size) {
    return repository.existsBySha256AndSize(sha256, size);
  }

  public List<EasinessStorageFile> getByDepend(String depend) {
    return repository.findAllByDependContains(depend);
  }
}
