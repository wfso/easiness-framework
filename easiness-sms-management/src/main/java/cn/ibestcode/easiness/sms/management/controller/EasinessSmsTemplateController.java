/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.controller;

import cn.ibestcode.easiness.core.base.controller.Controller;
import cn.ibestcode.easiness.core.paging.DefaultPageableGenerator;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsTemplate;
import cn.ibestcode.easiness.sms.management.query.SmsTemplateQueryVo;
import cn.ibestcode.easiness.sms.management.service.EasinessSmsTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:54
 */
@Api(tags = "短信模板管理")
@RequestMapping("/api/easiness/sms/template")
public class EasinessSmsTemplateController implements Controller<EasinessSmsTemplate, Long, SmsTemplateQueryVo, DefaultPageableGenerator> {

  @Autowired
  private EasinessSmsTemplateService smsTemplateService;

  @Override
  @PostMapping
  @ApiOperation("添加短信模")
  public EasinessSmsTemplate add(@RequestBody EasinessSmsTemplate smsTemplate) {
    return smsTemplateService.create(smsTemplate);
  }

  @Override
  @PutMapping
  @ApiOperation("修改短信模")
  public EasinessSmsTemplate edit(@RequestBody EasinessSmsTemplate smsTemplate) {
    return smsTemplateService.update(smsTemplate);
  }

  @Override
  @DeleteMapping(path = "{id}")
  @ApiOperation("删除短信模")
  public EasinessSmsTemplate remove(@PathVariable("id") Long id) {
    return smsTemplateService.removeById(id);
  }

  @Override
  @GetMapping(path = "{id}")
  @ApiOperation("获取短信模详情")
  public EasinessSmsTemplate info(@PathVariable("id") Long id) {
    return smsTemplateService.getById(id);
  }

  @Override
  @PostMapping(path = "page")
  @ApiOperation("短信模记录-POST-带分页")
  public Page<EasinessSmsTemplate> postPage(@RequestBody SmsTemplateQueryVo queryVo, DefaultPageableGenerator pageableGenerator) {
    return getPage(queryVo, pageableGenerator);
  }

  @Override
  @GetMapping
  @ApiOperation("smsTemplate记录-GET-带分页")
  public Page<EasinessSmsTemplate> getPage(SmsTemplateQueryVo queryVo, DefaultPageableGenerator pageableGenerator) {
    return smsTemplateService.getPage(queryVo.generateFilter(), pageableGenerator.generatePageable());
  }
}
