/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.converter;

import cn.ibestcode.easiness.form.domain.Item;
import cn.ibestcode.easiness.form.domain.NumberItem;
import cn.ibestcode.easiness.form.domain.StringItem;
import cn.ibestcode.easiness.form.model.FormData;
import cn.ibestcode.easiness.form.model.FormItem;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 13:28
 */

public class FormItemConverterTest {

  @Test
  public void spiTest() {
    Map<String, Item> itemMap = FormItemConverter.getDefaultItemMap();
    Assert.isTrue(
      itemMap.containsKey(NumberItem.class.getSimpleName())
        && itemMap.containsKey(StringItem.class.getSimpleName()),
      "spiTest Fail"
    );
  }

  @Test
  public void convertTest() {
    FormItem item = new FormItem();
    item.setType("NumberItem");
    NumberItem numberItem = FormItemConverter.to(item);
    Assert.notNull(numberItem, "convertTest Fail");
    numberItem = FormItemConverter.to(item, new FormData());
    Assert.notNull(numberItem, "convertTest Fail");
    FormItem formItem = FormItemConverter.from(numberItem);
    Assert.notNull(formItem, "convertTest Fail");
  }

}
