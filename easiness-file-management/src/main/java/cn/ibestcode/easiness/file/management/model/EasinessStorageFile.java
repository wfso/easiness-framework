/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.model;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import cn.ibestcode.easiness.core.converter.StringListConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
@Entity
@Table(name = "easiness_storage_file", indexes = {
  @Index(columnList = "uuid", name = "easiness_storage_file_uuid", unique = true),
  @Index(columnList = "outId", name = "easiness_storage_file_outId", unique = true),
  @Index(columnList = "sha256,size", name = "easiness_storage_file_sha256_size", unique = true),
  @Index(columnList = "deleted", name = "easiness_storage_file_deleted"),
  @Index(columnList = "url", name = "easiness_storage_file_url")
})
@ApiModel("文件存储")
@Data
public class EasinessStorageFile extends UuidBaseJpaModel {

  @Column(length = 64)
  @ApiModelProperty("文件的sha256")
  String sha256;

  @Column
  @ApiModelProperty("文件大小")
  private long size;

  @Column
  @ApiModelProperty("文件的URL")
  private String url;

  @Lob
  @Convert(converter = StringListConverter.class)
  @ApiModelProperty("被哪些模块依赖/使用")
  List<String> depend;

  @ApiModelProperty("文件的状态")
  @Column(length = 50)
  private StorageFileStatus fileStatus = StorageFileStatus.TEMPORARY;

  @Column
  @ApiModelProperty("软删除标记")
  private boolean deleted;

  @Column
  @ApiModelProperty("当文件被存储到第三方储存平台时，用于存储文件在第三方平台上的ID；当文件在本地存储时，则用于存储文件的绝对路径")
  private String outId;

  /**
   * 单独定义的 getter 方法，
   * 在返回之前检查 depend 属性是否为 null
   * 如果为 null 则进行初始化
   *
   * @return depend 属性
   */
  public List<String> getDepend() {
    if (depend == null) {
      depend = new ArrayList<>();
    }
    return depend;
  }

}
