/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.converter;

import cn.ibestcode.easiness.form.domain.NumberItem;
import cn.ibestcode.easiness.form.model.FormData;
import cn.ibestcode.easiness.form.model.FormItem;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 13:27
 */
public class NumberItemConverterProvider implements ItemConverterProvider<NumberItem> {
  @Override
  public NumberItem to(FormItem item) {
    return new NumberItem();
  }

  @Override
  public NumberItem to(FormItem item, FormData data) {
    return new NumberItem();
  }

  @Override
  public FormItem from(NumberItem numberItem) {
    return new FormItem();
  }
}
