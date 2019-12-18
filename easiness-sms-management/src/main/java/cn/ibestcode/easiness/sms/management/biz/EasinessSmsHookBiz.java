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
import cn.ibestcode.easiness.sendsms.hook.SmsHookResultItem;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsRecord;
import cn.ibestcode.easiness.sms.management.service.EasinessSmsRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:30
 */
@Biz
public class EasinessSmsHookBiz extends EasinessSmsHookBus {

  @Autowired
  protected EasinessSmsRecordService smsRecordService;

  @Override
  public String hook(String type) {
    SmsHookResult result = doHook(type);
    if (result != null) {
      List<SmsHookResultItem> items = result.getItems();
      for (SmsHookResultItem item : items) {
        if (StringUtils.isNotEmpty(item.getSendId())) {
          EasinessSmsRecord smsRecord = smsRecordService.getBySendIdAndSenderType(item.getSendId(), item.getSenderType());
          if (smsRecord != null) {
            smsRecord.setSmsStatus(item.getStatus());
            smsRecord.setIntro(item.getIntro());
            smsRecord.setHookResult(item.toJSON());
            smsRecordService.update(smsRecord);
          }
        }
      }
      return result.getResponse();
    }
    throw new NullPointerException("SmsHookResultCanNotBeNull");
  }
}
