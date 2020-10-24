/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.converter;

import cn.ibestcode.easiness.form.domain.*;
import cn.ibestcode.easiness.form.limit.Limit;
import cn.ibestcode.easiness.form.limit.NumberLimit;
import cn.ibestcode.easiness.form.limit.StringLimit;
import cn.ibestcode.easiness.form.model.FormItem;
import cn.ibestcode.easiness.form.model.FormPattern;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 13:28
 */
@Slf4j
public class FormConverterTest {

  @Test
  public void spiTest() {
    Map<String, Limit> itemMap = FormConverter.getAllLimitMap();
    Assert.isTrue(
      itemMap.containsKey(NumberLimit.class.getSimpleName())
        && itemMap.containsKey(StringLimit.class.getSimpleName()),
      "spiTest Fail"
    );
  }

  @Test
  public void itemConvertTest() {
    DefaultItem defaultItem, tdi;
    FormItem formItem, tfi;

    defaultItem = new DefaultItem();
    defaultItem.setLimit(new NumberLimit());
    log.warn(defaultItem.toString());

    formItem = FormConverter.convert(defaultItem);
    log.warn(formItem.toString());

    tdi = FormConverter.convert(formItem);
    log.warn(tdi.toString());

    tfi = FormConverter.convert(tdi);
    log.warn(tfi.toString());

    Assert.isTrue(defaultItem.equals(tdi), "itemConvertTest Fail");
    Assert.isTrue(formItem.toString().equals(tfi.toString()), "itemConvertTest Fail");

  }

  @Test
  public void formConvertTest() {
    DefaultForm defaultForm, tdf;
    FormPattern formPattern, tfp;

    DefaultItem defaultItem = new DefaultItem();
    defaultItem.setLimit(new StringLimit());
    defaultForm = new DefaultForm();
    defaultForm.addItem(defaultItem);
    defaultItem = new DefaultItem();
    defaultItem.setLimit(new NumberLimit());
    defaultForm.addItem(defaultItem);
    log.warn(defaultForm.toString());

    formPattern = FormConverter.convert(defaultForm);
    log.warn(formPattern.toString());

    tdf = FormConverter.convert(formPattern);
    log.warn(tdf.toString());

    tfp = FormConverter.convert(tdf);
    log.warn(tfp.toString());

    Assert.isTrue(defaultForm.equals(tdf),"formConvertTest Fail");
    Assert.isTrue(formPattern.toString().equals(tfp.toString()),"formConvertTest Fail");

  }

  @Test
  public void genLimitTest() {
    Map<String, String> map = new HashMap<>();
    map.put("min", "3");
    map.put("max", "7");
    NumberLimit numberLimit = new NumberLimit();
    numberLimit.setMin(3);
    numberLimit.setMax(7);
    Limit limit = FormConverter.genLimit("NumberLimit", map);
    log.warn(limit.toString());
    log.warn(numberLimit.toString());
    Assert.isTrue(numberLimit.equals(limit), "genLimitTest Fail");

  }

}
