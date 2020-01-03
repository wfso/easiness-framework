/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.scheduling;

import cn.ibestcode.easiness.core.query.builder.DefaultFiltersBuilder;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import cn.ibestcode.easiness.file.management.biz.EasinessStorageFileBiz;
import cn.ibestcode.easiness.file.management.model.EasinessStorageFile;
import cn.ibestcode.easiness.file.management.model.StorageFileStatus;
import cn.ibestcode.easiness.file.management.service.EasinessStorageFileService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
@Component
@EnableScheduling
@Slf4j
public class EasinessStorageFileClearScheduling {

  private static final String garbageFlag = "easiness-file-management-EasinessStorageFileClearScheduling-fileClearGarbage";
  private static final String temporaryFlag = "easiness-file-management-EasinessStorageFileClearScheduling-fileClearTemporary";

  @Autowired
  private RedissonClient redissonClient;

  @Autowired
  private EasinessStorageFileService storageFileService;

  @Autowired
  private EasinessStorageFileBiz storageFileBiz;

  private static final long garbageTimeout = 24 * 60 * 60 * 1000;

  @Scheduled(cron = "0 0 2,3,4,5 * * ?")
  public void fileClearGarbage() {
    RLock lock = redissonClient.getLock(garbageFlag);

    try {
      if (lock.tryLock(0, 1, TimeUnit.HOURS)) {
        IFilter filter = DefaultFiltersBuilder.getAndInstance()
          .andIsFalse("deleted")
          .andEqual("fileStatus", StorageFileStatus.GARBAGE.name(), StorageFileStatus.class)
          .andLessThen("updatedAt", String.valueOf(System.currentTimeMillis() - garbageTimeout))
          .build();
        List<EasinessStorageFile> files = storageFileService.getList(filter);
        int i = 0;
        for (EasinessStorageFile file : files) {
          i++;
          storageFileBiz.sremove(file.getUuid());
          if (i > 100) {
            break;
          }
        }

      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      log.warn(e.getMessage(), e);
    }

  }

  @Scheduled(cron = "0 30 2,3,4,5 * * ?")
  public void fileClearTemporary() {
    RLock lock = redissonClient.getLock(temporaryFlag);

    try {
      if (lock.tryLock(0, 1, TimeUnit.HOURS)) {
        IFilter filter = DefaultFiltersBuilder.getAndInstance()
          .andIsFalse("deleted")
          .andEqual("fileStatus", StorageFileStatus.TEMPORARY.name(), StorageFileStatus.class)
          .andLessThen("updatedAt", String.valueOf(System.currentTimeMillis() - garbageTimeout))
          .build();
        List<EasinessStorageFile> files = storageFileService.getList(filter);
        int i = 0;
        for (EasinessStorageFile file : files) {
          i++;
          storageFileBiz.garbage(file.getUuid());
          if (i > 100) {
            break;
          }
        }

      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      log.warn(e.getMessage(), e);
    }

  }
}
