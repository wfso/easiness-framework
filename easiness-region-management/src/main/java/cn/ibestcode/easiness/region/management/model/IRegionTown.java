/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.model;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/17 20:13
 */
public interface IRegionTown extends IRegionCounty {
  String getTownCode();

  String getTownName();

  void setTownCode(String code);

  void setTownName(String name);
}
