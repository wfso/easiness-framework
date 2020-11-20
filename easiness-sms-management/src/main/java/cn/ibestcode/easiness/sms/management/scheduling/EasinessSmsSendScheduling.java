/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.scheduling;

import cn.ibestcode.easiness.core.properties.EasinessApplicationProperties;
import cn.ibestcode.easiness.sendsms.sender.SmsSender;
import cn.ibestcode.easiness.sendsms.sender.SmsSenderResult;
import cn.ibestcode.easiness.sms.management.model.EasinessSmsCrontab;
import cn.ibestcode.easiness.sendsms.hook.EasinessSmsStatus;
import cn.ibestcode.easiness.sms.management.service.EasinessSmsCrontabService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/20 20:13
 */
@Component
@EnableScheduling
@Slf4j
public class EasinessSmsSendScheduling {

  @Autowired
  private RedissonClient redissonClient;

  @Autowired
  private EasinessSmsCrontabService smsService;

  @Autowired
  private SmsSender smsSender;

  @Autowired
  private EasinessApplicationProperties applicationProperties;

  @Scheduled(cron = "0 * * * * ?")
  public void sendSms() {
    RLock lock = redissonClient.getLock(applicationProperties.getId() + "easiness-sms-crontab-EasinessSmsSendScheduling-sendSms");
    try {
      if (lock.tryLock(0, 1, TimeUnit.HOURS)) {
        List<EasinessSmsCrontab> smsList = smsService.getBySmsStatusAndSendAtLessThenEqual(EasinessSmsStatus.UNSENT, System.currentTimeMillis());
        for (EasinessSmsCrontab sms : smsList) {
          SmsSenderResult result = smsSender.sendSms(sms.getPhone(), sms.getTemplate(), sms.getVars());
          sms.setSmsStatus(EasinessSmsStatus.SENDING);
          sms.setSendId(result.getId());
          smsService.update(sms);
        }
        lock.unlock();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      log.warn(e.getMessage(), e);
    }
  }
}
