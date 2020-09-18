/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.biz;

import cn.ibestcode.easiness.core.annotation.Biz;
import cn.ibestcode.easiness.file.management.clear.EasinessStorageFileDependDetector;
import cn.ibestcode.easiness.file.management.model.EasinessStorageFile;
import cn.ibestcode.easiness.file.management.model.StorageFileStatus;
import cn.ibestcode.easiness.file.management.service.EasinessStorageFileService;
import cn.ibestcode.easiness.storage.Storage;
import cn.ibestcode.easiness.storage.StorageResult;
import cn.ibestcode.easiness.utils.DigestUtil;
import cn.ibestcode.easiness.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
@Biz
public class EasinessStorageFileBiz {

  private static final FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy/MM/dd");

  @Autowired
  private EasinessStorageFileDependDetector fileDependDetector;

  @Autowired
  private EasinessStorageFileService fileService;

  @Autowired
  private Storage storage;

  @Transactional
  public EasinessStorageFile addDependByUuid(String uuid, String module) {
    EasinessStorageFile file = fileService.getByUuid(uuid);
    return addDepend(file, module);
  }

  @Transactional
  public EasinessStorageFile addDependByUrl(String url, String module) {
    EasinessStorageFile file = fileService.getByUrl(url);
    return addDepend(file, module);
  }

  @Transactional
  public EasinessStorageFile addDependBySha256(String sha256, long size, String module) {
    EasinessStorageFile file = fileService.getBySha256AndSize(sha256, size);
    return addDepend(file, module);
  }

  @Transactional
  public EasinessStorageFile addDepend(EasinessStorageFile file, String module) {
    if (file != null) {
      file.getDepend().add(module);
      file.setFileStatus(StorageFileStatus.USED);
      file.setDeleted(false);
      fileService.update(file);
    }
    return file;
  }

  @Transactional
  public EasinessStorageFile removeDependByUuid(String uuid, String module) {
    EasinessStorageFile file = fileService.getByUuid(uuid);
    return removeDepend(file, module);
  }

  @Transactional
  public EasinessStorageFile removeDependByUrl(String url, String module) {
    EasinessStorageFile file = fileService.getByUrl(url);
    return removeDepend(file, module);
  }

  @Transactional
  public EasinessStorageFile removeDepend(EasinessStorageFile file, String module) {
    if (file != null) {
      file.getDepend().remove(module);
      if (file.getDepend().size() == 0) {
        file.setFileStatus(StorageFileStatus.GARBAGE);
      }
      fileService.update(file);
    }
    return file;
  }

  public EasinessStorageFile garbage(String uuid) {
    EasinessStorageFile file = fileService.getByUuid(uuid);
    if (file != null && !fileDependDetector.dependOn(file, true)) {
      file.setFileStatus(StorageFileStatus.GARBAGE);
      fileService.update(file);
    }
    return file;
  }

  @Transactional
  public EasinessStorageFile sremove(String uuid) {
    EasinessStorageFile file = fileService.getByUuid(uuid);
    if (file != null && !fileDependDetector.dependOn(file, true)) {
      file.setDeleted(true);
      file.setFileStatus(StorageFileStatus.DELETED);
      fileService.update(file);
    }
    return file;
  }

  @Transactional
  public EasinessStorageFile remove(String uuid) {
    EasinessStorageFile file = fileService.getByUuid(uuid);
    if (file != null && !fileDependDetector.dependOn(file, true)) {
      storage.removeFile(file.getOutId());
      fileService.remove(file);
    }
    return file;
  }

  @Transactional
  public EasinessStorageFile upload(MultipartFile file) {
    EasinessStorageFile storageFile = null;
    try {
      String sha256 = DigestUtil.sha256Hex(file.getBytes());
      long size = file.getSize();
      storageFile = fileService.getBySha256AndSize(sha256, size);
      if (storageFile == null) {
        storageFile = new EasinessStorageFile();
        storageFile.setFileStatus(StorageFileStatus.TEMPORARY);
        storageFile.setSha256(sha256);
        storageFile.setSize(size);
        String suffix = getSuffix(file.getOriginalFilename());
        String fileName = genFileName(suffix);
        StorageResult result = storage.putFile(fileName, file.getInputStream());
        storageFile.setOutId(result.getId());
        storageFile.setUrl(result.getUrl());
        fileService.create(storageFile);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return storageFile;
  }


  private String getSuffix(String fileName) {
    if (StringUtils.isBlank(fileName)) {
      return "";
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  private String genFileName(String suffix) {
    if (StringUtils.isBlank(suffix)) {
      suffix = "";
    }
    suffix = suffix.toLowerCase();
    return fastDateFormat.format(System.currentTimeMillis()) + "/" + RandomUtil.generateUnseparatedUuid() + "." + suffix;
  }

}
