/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.vo;

import cn.ibestcode.easiness.core.query.builder.AndFiltersBuilder;
import cn.ibestcode.easiness.core.query.builder.DefaultFiltersBuilder;
import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import cn.ibestcode.easiness.file.management.model.StorageFileStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
@ApiModel("文件存储查询对象")
@Data
public class EasinessStorageFileQueryVo implements FilterGenerator {
  @ApiModelProperty("是否已经删除")
  private Boolean deleted;

  @ApiModelProperty("文件的URL")
  private String url;

  @ApiModelProperty("文件被哪个模块依赖")
  private String module;

  @ApiModelProperty("状态")
  private StorageFileStatus  fileStatus;

  @Override
  public IFilter generateFilter() {
    AndFiltersBuilder builder = DefaultFiltersBuilder.getAndInstance();
    if (deleted != null) {
      if (deleted) {
        builder.andIsTrue("deleted");
      } else {
        builder.andIsFalse("deleted");
      }
    }
    if (fileStatus != null) {
      builder.andEqual("fileStatus", fileStatus.name(), StorageFileStatus.class);
    }
    builder.andContain("url", url)
      .andContain("depend", module);
    return builder.build();
  }
}
