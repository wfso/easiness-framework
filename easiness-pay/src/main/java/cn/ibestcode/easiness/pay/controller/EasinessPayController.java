/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.controller;

import cn.ibestcode.easiness.pay.biz.EasinessPayBiz;
import cn.ibestcode.easiness.pay.handler.EasinessPayNotifyHandlerBus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 22:13
 */
@RestController
@RequestMapping("/api/easiness/pay")
@Api(tags = "支付接口")
public class EasinessPayController {
  @Autowired
  private EasinessPayNotifyHandlerBus notifyHandlerBus;
  @Autowired
  private EasinessPayBiz easinessPayBiz;

  /**
   * 支付的异步通知接口
   *
   * @param payUuid 支付实体的UUID
   * @return 根据对应支付平台要求返回字符串
   */
  @RequestMapping("notify/{payType}/{payUuid}")
  @ApiOperation("支付平台异步通知接收接口")
  public String easinessPayAsyncNotification(@PathVariable String payType, @PathVariable String payUuid) {
    return notifyHandlerBus.notifyHandle(payType, payUuid);
  }

  @PutMapping("cancel/{payUuid}")
  @ApiOperation("取消支付")
  public void easinessPayCancel(@PathVariable String payUuid) {
    easinessPayBiz.setPayStatusCancel(payUuid);
  }
}
