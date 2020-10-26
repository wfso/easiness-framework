/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.limit;

import lombok.Data;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 13:26
 */
@Data
public class StringLimit implements Limit {
  private int min = 0;
  private int max = Integer.MAX_VALUE;
  private String pattern = ".*";

  @Override
  public boolean check(String value) {
    return true;
  }
}
