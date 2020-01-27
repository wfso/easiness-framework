/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.domain;

import cn.ibestcode.easiness.region.management.model.EasinessRegion;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/17 20:13
 */
@Getter
@Setter
public class RegionVo implements Serializable {

  public RegionVo() {
  }

  public RegionVo(EasinessRegion region) {
    this.c = region.getCode();
    this.n = region.getName();
  }

  @JsonAlias("code")
  private String c;

  @JsonAlias("name")
  private String n;
}
