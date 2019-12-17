/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.biz;

import cn.ibestcode.easiness.core.annotation.Biz;
import cn.ibestcode.easiness.sendsms.hook.EasinessSmsHookBus;
import cn.ibestcode.easiness.sendsms.hook.SmsHookResult;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsRecord;
import cn.ibestcode.easiness.sms.management.service.EasinessSmsRecordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:30
 */
@Biz
public class EasinessSmsHookBiz extends EasinessSmsHookBus{

  @Autowired
  protected EasinessSmsRecordService smsRecordService;

  @Override
  public String hook(String type) {
    SmsHookResult result = doHook(type);
    EasinessSmsRecord smsRecord = smsRecordService.getBySendIdAndSenderType(result.getSendId(), result.getSenderType());
    if (smsRecord != null) {
      smsRecord.setSmsStatus(result.getStatus());
      smsRecord.setIntro(result.getIntro());
      smsRecord.setHookResult(result.toJSON());
      smsRecordService.update(smsRecord);
    }
    return result.getResponse();
  }
}
