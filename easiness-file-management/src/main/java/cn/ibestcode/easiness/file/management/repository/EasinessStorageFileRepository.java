/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.repository;


import cn.ibestcode.easiness.core.base.repository.UuidBaseJpaRepository;
import cn.ibestcode.easiness.file.management.model.EasinessStorageFile;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
public interface EasinessStorageFileRepository extends UuidBaseJpaRepository<EasinessStorageFile> {
  EasinessStorageFile findBySha256AndSize(String sha256, long size);

  EasinessStorageFile findByUrl(String url);

  List<EasinessStorageFile> findAllByDependContains(String depend);

  boolean existsBySha256AndSize(String sha256, long size);
}
