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
public class NumberLimit implements Limit {
  private int minSize = 0;
  private int maxSize = Integer.MAX_VALUE;

  @Override
  public boolean check(String value) {
    return true;
  }
}
