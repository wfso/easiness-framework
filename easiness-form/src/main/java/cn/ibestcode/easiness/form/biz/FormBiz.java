/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.biz;

import cn.ibestcode.easiness.auth.biz.EasinessAuthBiz;
import cn.ibestcode.easiness.core.annotation.Biz;
import cn.ibestcode.easiness.form.model.FormPattern;
import cn.ibestcode.easiness.form.service.FormDataService;
import cn.ibestcode.easiness.form.service.FormItemService;
import cn.ibestcode.easiness.form.service.FormPatternService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/27 10:30
 */
@Biz
public class FormBiz {
  @Autowired
  private FormPatternService formPatternService;

  @Autowired
  private FormItemService formItemService;

  @Autowired
  private FormDataService formDataService;

  @Autowired
  private EasinessAuthBiz authBiz;

  public FormPattern createFormPattern(FormPattern formPattern) {
    return formPatternService.create(formPattern);
  }

  public FormPattern updateFormPattern(FormPattern formPattern) {
    return null;
  }

  public FormPattern getFormPattern(String formUuid) {
    return null;
  }

}
