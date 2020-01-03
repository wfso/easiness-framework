/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.clear;

import cn.ibestcode.easiness.file.management.model.EasinessStorageFile;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
public interface EasinessStorageFileDependChecker {
  /**
   * 1. 当 force 为 true 时，表示要强制进行依赖检查：
   * 无论模块是否在 easinessStorageFile.usedModules 中有记录，均需要进行依赖检查，
   * 如果发现依赖，则把模块名加入到 easinessStorageFile.usedModules 中（已有时不需要重复加），并反回true；
   * 如果没有依赖，则从 easinessStorageFile.usedModules 中删除模块名（没有时不需要删除），并返回 false；
   * <p>
   * 2. 当 farce 为 false 时，表示不强制进行依赖检查：
   * 只有当模块在 easinessStorageFile.usedModules 中有记录时，才进行依赖检查，否则直接返回false；
   * 如果发现依赖 则返回 true;
   * 如果没有发现依赖 则从 easinessStorageFile.usedModules 中删除模块名，并返回 false；
   *
   * @param easinessStorageFile EasinessStorageFile 对象
   * @param force            是否强制检查
   * @return boolean
   */
  boolean dependOn(EasinessStorageFile  easinessStorageFile, boolean force);

  default boolean dependOn(EasinessStorageFile  easinessStorageFile) {
    return dependOn(easinessStorageFile, false);
  }
}
