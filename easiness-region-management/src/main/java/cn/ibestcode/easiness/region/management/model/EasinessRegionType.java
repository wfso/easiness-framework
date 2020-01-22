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
public enum EasinessRegionType {

  NATIONALITY("国家"),

  PROVINCE("省"),

  CITY("市"),

  COUNTY("区、县"),

  TOWN("乡、镇、街道");


  /**
   * 文字描述
   */
  private final String name;

  EasinessRegionType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
