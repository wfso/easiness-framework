/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.controller;

import cn.ibestcode.easiness.core.paging.DefaultPageableGenerator;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsRecord;
import cn.ibestcode.easiness.sms.management.query.SmsRecordQueryVo;
import cn.ibestcode.easiness.sms.management.service.EasinessSmsRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:14
 */
@Api(tags = "短信记录管理")
@RequestMapping("/api/easiness/sms/record")
public class EasinessSmsRecordController {

  @Autowired
  private EasinessSmsRecordService smsRecordService;

  @GetMapping
  @ApiOperation("短信发送记录-GET-带分页")
  public Page<EasinessSmsRecord> getPage(SmsRecordQueryVo queryVo, DefaultPageableGenerator pageableGenerator) {
    return smsRecordService.getPage(queryVo, pageableGenerator.generatePageable());
  }

  @PostMapping
  @ApiOperation("短信发送记录-POST-带分页")
  public Page<EasinessSmsRecord> postPage(@RequestBody SmsRecordQueryVo queryVo, DefaultPageableGenerator pageableGenerator) {
    return smsRecordService.getPage(queryVo, pageableGenerator.generatePageable());
  }

  @PutMapping
  @ApiOperation("修改短信发送记录")
  public void edit(@RequestBody EasinessSmsRecord smsRecord) {
    smsRecordService.updateByUuidIgnoreNull(smsRecord);
  }
}
