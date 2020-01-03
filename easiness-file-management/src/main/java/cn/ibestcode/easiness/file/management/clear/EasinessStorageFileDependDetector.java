/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.file.management.clear;

import cn.ibestcode.easiness.file.management.model.EasinessStorageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/03 20:13
 */
@Component
public class EasinessStorageFileDependDetector {

  @Autowired
  List<EasinessStorageFileDependChecker> checkerList;

  public boolean dependOn(EasinessStorageFile easinessStorageFile) {
    return dependOn(easinessStorageFile, false);
  }

  /**
   * 对所有依赖了 easinessStorageFile 的模块进行依赖检查；
   * 如果有模块对 easinessStorageFile 有依赖则返回true；
   * 如果所有模块均对 easinessStorageFile 无依赖则返回 false;
   *
   * @param easinessStorageFile EasinessStorageFile 对象
   * @param force            为true时 强制检查所有模块，
   *                         为false时 遇到对 easinessStorageFile 依赖的
   *                         模块后则返回true，不进行后续查检；
   * @return
   */
  public boolean dependOn(EasinessStorageFile easinessStorageFile, boolean force) {
    boolean result = false;
    for (EasinessStorageFileDependChecker checker : checkerList) {
      // 如果模块对 easinessStorageFile 有依赖 则设置返回结果为 true
      if (checker.dependOn(easinessStorageFile, force)) {
        result = true;

        // 如果不 强制检查所有 则可以退出环境了
        if (!force) {
          break;
        }
      }
    }
    return result;
  }

}
