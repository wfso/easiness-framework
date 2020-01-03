/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.model;

import lombok.Getter;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
public enum StorageFileStatus {
  /**
   * 临时文件
   */
  TEMPORARY("临时文件"),

  /**
   * 使用中
   */
  USED("使用中"),

  /**
   * 垃圾文件
   */
  GARBAGE("垃圾文件"),

  /**
   * 软删除
   */
  DELETED("软删除");

  @Getter
  private String text;

  private StorageFileStatus(String text) {
    this.text = text;
  }
}
