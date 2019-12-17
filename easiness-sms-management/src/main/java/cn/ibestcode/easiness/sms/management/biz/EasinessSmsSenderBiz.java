/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.biz;

import cn.ibestcode.easiness.core.annotation.Biz;
import cn.ibestcode.easiness.sendsms.sender.EasinessSmsSender;
import cn.ibestcode.easiness.sendsms.sender.SmsSenderResult;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsRecord;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsStatus;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsTemplate;
import cn.ibestcode.easiness.sms.management.service.EasinessSmsRecordService;
import cn.ibestcode.easiness.sms.management.service.EasinessSmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/17 19:19
 */
@Biz
public class EasinessSmsSenderBiz extends EasinessSmsSender {

  @Autowired
  private EasinessSmsRecordService smsRecordService;

  @Autowired
  private EasinessSmsTemplateService templateService;

  @Override
  public SmsSenderResult sendSms(String phone, String template, Map<String, String> vars, String type) {
    EasinessSmsRecord record = new EasinessSmsRecord();
    EasinessSmsTemplate smsTemplate = templateService.getByTemplate(template);
    if (smsTemplate != null) {
      record.setContent(smsTemplate.getContent());
    }
    record.setPhone(phone);
    record.setSenderType(type);
    record.setTemplate(template);
    record.setVars(vars);
    record.setSmsStatus(EasinessSmsStatus.UNSENT);
    smsRecordService.create(record);
    SmsSenderResult result = super.sendSms(phone, template, vars, type);
    record.setSendResult(result.toJSON());
    record.setSmsStatus(EasinessSmsStatus.SENDING);
    record.setSendId(result.getId());
    smsRecordService.update(record);
    return result;
  }

  @Override
  public SmsSenderResult sendSms(String phone, String template, Map<String, String> vars) {
    return this.sendSms(phone, template, vars, type);
  }
}
