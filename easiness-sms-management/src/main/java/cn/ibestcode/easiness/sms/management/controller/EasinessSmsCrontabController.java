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
import cn.ibestcode.easiness.sms.management.model.EasinessSmsCrontab;
import cn.ibestcode.easiness.sms.management.query.SmsCrontabQueryVo;
import cn.ibestcode.easiness.sms.management.service.EasinessSmsCrontabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:54
 */
@RestController
@Api(tags = "定时短信管理")
@RequestMapping("/api/easiness/sms/crontab")
public class EasinessSmsCrontabController implements Controller<EasinessSmsCrontab, Long, SmsCrontabQueryVo, DefaultPageableGenerator> {

  @Autowired
  private EasinessSmsCrontabService SmsCrontabService;

  @Override
  @PostMapping
  @ApiOperation("添加定时短信")
  public EasinessSmsCrontab add(@RequestBody EasinessSmsCrontab smsCrontab) {
    return SmsCrontabService.create(smsCrontab);
  }

  @Override
  @PutMapping
  @ApiOperation("修改定时短信")
  public EasinessSmsCrontab edit(@RequestBody EasinessSmsCrontab smsCrontab) {
    return SmsCrontabService.update(smsCrontab);
  }

  @Override
  @DeleteMapping(path = "{id}")
  @ApiOperation("删除定时短信")
  public EasinessSmsCrontab remove(@PathVariable("id") Long id) {
    return SmsCrontabService.removeById(id);
  }

  @Override
  @GetMapping(path = "{id}")
  @ApiOperation("获取定时短信详情")
  public EasinessSmsCrontab info(@PathVariable("id") Long id) {
    return SmsCrontabService.getById(id);
  }

  @Override
  @PostMapping(path = "page")
  @ApiOperation("定时短信记录-POST-带分页")
  public Page<EasinessSmsCrontab> postPage(@RequestBody SmsCrontabQueryVo queryVo, DefaultPageableGenerator pageableGenerator) {
    return getPage(queryVo, pageableGenerator);
  }

  @Override
  @GetMapping
  @ApiOperation("定时短信记录-GET-带分页")
  public Page<EasinessSmsCrontab> getPage(SmsCrontabQueryVo queryVo, DefaultPageableGenerator pageableGenerator) {
    return SmsCrontabService.getPage(queryVo.generateFilter(), pageableGenerator.generatePageable());
  }
}
