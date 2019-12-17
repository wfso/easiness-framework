/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.helper;

import cn.ibestcode.easiness.sms.management.model.EasinessSmsCrontab;
import cn.ibestcode.easiness.sendsms.hook.EasinessSmsStatus;
import cn.ibestcode.easiness.utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/20 19:13
 */
public class EasinessSmsHelper {

  public static EasinessSmsCrontab getInstance(String phone, String template, Map<String, String> vars, long sendAt, String group) {
    EasinessSmsCrontab sms = new EasinessSmsCrontab();
    sms.setPhone(phone);
    sms.setTemplate(template);
    sms.setVars(vars);
    sms.setSendAt(sendAt);
    sms.setSmsGroup(group);
    sms.setSmsStatus(EasinessSmsStatus.UNSENT);
    sms.setComplete(false);
    return sms;
  }

  public static EasinessSmsCrontab getInstance(String phone, String template, Map<String, String> vars, long sendAt) {
    return getInstance(phone, template, vars, sendAt, RandomUtil.generateUnseparatedUuid());
  }

  public static EasinessSmsCrontab getInstance(String phone, String template, Map<String, String> vars) {
    return getInstance(phone, template, vars, System.currentTimeMillis());
  }

  public static List<EasinessSmsCrontab> getInstances(List<String> phones, String template, Map<String, String> vars, long sendAt, String group) {
    List<EasinessSmsCrontab> smsList = new ArrayList<>();
    for (String phone : phones) {
      smsList.add(getInstance(phone, template, vars, sendAt, group));
    }
    return smsList;
  }

}
