/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.controller;

import cn.ibestcode.easiness.form.converter.FormConverter;
import cn.ibestcode.easiness.form.limit.Limit;
import cn.ibestcode.easiness.form.model.FormPattern;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:29
 */
@RestController
@RequestMapping("/api/form")
@Api(tags = "表单模式管理")
public class FormPatternController {
  @GetMapping("item/limits")
  @ApiOperation("表单项的类型/限制")
  public Map<String, Limit> getItemTypes() {
    return FormConverter.getAllLimitMap();
  }

  @PostMapping
  @ApiOperation("创建一个表单")
  public void create(FormPattern formPattern) {

  }

  @PutMapping
  @ApiOperation("修改一个表单")
  public void edit(FormPattern formPattern) {

  }

  @GetMapping
  @ApiOperation("获取一个表单模式")
  public FormPattern info(String formUuid) {
    return null;
  }

  @GetMapping("list")
  @ApiOperation("表单模式列表")
  public List<FormPattern> list() {
    return null;
  }

}
