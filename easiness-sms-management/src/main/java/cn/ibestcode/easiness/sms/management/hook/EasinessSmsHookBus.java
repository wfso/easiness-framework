/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.hook;

import cn.ibestcode.easiness.sms.management.exception.SmsManagementException;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsRecord;
import cn.ibestcode.easiness.sms.management.service.EasinessSmsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:30
 */
@Component
public class EasinessSmsHookBus {

  @Autowired
  protected List<EasinessSmsHook> smsHookList;

  @Autowired
  protected EasinessSmsRecordService smsRecordService;

  protected Map<String, EasinessSmsHook> smsHookMap;

  public String hook(String type) {
    EasinessSmsHook hook = getHookByType(type);
    if (hook == null) {
      throw new SmsManagementException("HookCanNotBeNull");
    }
    SmsHookResult result = hook.hook();
    EasinessSmsRecord smsRecord = smsRecordService.getBySendIdAndSenderType(result.getSendId(), result.getSenderType());
    if (smsRecord != null) {
      smsRecord.setSmsStatus(result.getStatus());
      smsRecord.setIntro(result.getIntro());
      smsRecord.setHookResult(result.toJSON());
      smsRecordService.update(smsRecord);
    }
    return result.getResponse();
  }

  private EasinessSmsHook getHookByType(String type) {
    if (smsHookMap == null) {
      smsHookMap = new HashMap<>();
      for (EasinessSmsHook hook : smsHookList) {
        smsHookMap.put(hook.supportType(), hook);
      }
    }
    if (!smsHookMap.containsKey(type)) {
      throw new SmsManagementException("NotHookExistOfType", type);
    }
    return smsHookMap.get(type);
  }
}
