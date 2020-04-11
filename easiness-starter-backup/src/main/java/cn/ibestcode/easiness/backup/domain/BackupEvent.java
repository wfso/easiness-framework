/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.backup.domain;

import cn.ibestcode.easiness.backup.entity.BackupMetadata;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/4/11 22:50
 */
@Getter
@Setter
public class BackupEvent {
  private BackupMetadata metadata;
  private Object object;

  public BackupEvent(BackupMetadata metadata, Object object) {
    this.metadata = metadata;
    this.object = object;
  }

}
