/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.controller;

import cn.ibestcode.easiness.sms.management.hook.EasinessSmsHookBus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:54
 */
@RestController
@Api(tags = "短信回调管理")
@RequestMapping("/api/easiness/sms/hook")
public class EasinessSmsHookController {

  @Autowired
  private EasinessSmsHookBus smsHookBus;

  @RequestMapping("{type}")
  @ApiOperation("发送结果回调")
  public String hook(@PathVariable String type) {
    return smsHookBus.hook(type);
  }
}
