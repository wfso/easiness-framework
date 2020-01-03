/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.controller;

import cn.ibestcode.easiness.core.paging.DefaultPageableGenerator;
import cn.ibestcode.easiness.file.management.biz.EasinessStorageFileBiz;
import cn.ibestcode.easiness.file.management.model.EasinessStorageFile;
import cn.ibestcode.easiness.file.management.service.EasinessStorageFileService;
import cn.ibestcode.easiness.file.management.vo.EasinessStorageFileQueryVo;
import cn.ibestcode.easiness.file.management.vo.EasinessStorageFileUploadVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
@Api(tags = "文件存储管理")
@RequestMapping("/api/easiness/storage/file")
public class EasinessStorageFileController {

  @Autowired
  private EasinessStorageFileBiz fileBiz;

  @Autowired
  private EasinessStorageFileService fileService;

  @PostMapping
  @ApiOperation("上传文件")
  public EasinessStorageFileUploadVo upload(@RequestParam MultipartFile file) {
    EasinessStorageFile storageFile = fileBiz.upload(file);
    EasinessStorageFileUploadVo uploadVo = new EasinessStorageFileUploadVo();
    if (storageFile != null) {
      uploadVo.setUuid(storageFile.getUuid());
      uploadVo.setUrl(storageFile.getUrl());
    }
    return uploadVo;
  }

  @GetMapping("check")
  @ApiOperation("检查文件是否存在")
  public EasinessStorageFileUploadVo checkFile(@RequestParam String sha256, @RequestParam long size) {
    EasinessStorageFile storageFile = fileService.getBySha256AndSize(sha256, size);
    EasinessStorageFileUploadVo uploadVo = new EasinessStorageFileUploadVo();
    if (storageFile != null) {
      uploadVo.setUuid(storageFile.getUuid());
      uploadVo.setUrl(storageFile.getUrl());
    }
    return uploadVo;
  }

  @DeleteMapping("{uuid}")
  @ApiOperation("删除文件")
  public void remove(@PathVariable String uuid) {
    fileBiz.remove(uuid);
  }

  @GetMapping
  @ApiOperation("文件列表-可分页")
  public Page<EasinessStorageFile> page(EasinessStorageFileQueryVo filterGenerator, DefaultPageableGenerator pageableGenerator) {
    return fileService.getPage(filterGenerator.generateFilter(), pageableGenerator.generatePageable());
  }


}
