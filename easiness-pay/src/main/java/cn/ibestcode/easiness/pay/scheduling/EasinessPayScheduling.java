/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.scheduling;

import cn.ibestcode.easiness.core.properties.EasinessApplicationProperties;
import cn.ibestcode.easiness.pay.biz.EasinessPayBiz;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.model.PayStatus;
import cn.ibestcode.easiness.pay.service.EasinessPayService;
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
 * create by WFSO (仵士杰) at 2020/01/07 22:12
 */
@Component
@EnableScheduling
@Slf4j
public class EasinessPayScheduling {

  @Autowired
  private EasinessPayBiz payBiz;

  @Autowired
  private EasinessPayService payService;

  @Autowired
  private RedissonClient redissonClient;

  @Autowired
  private EasinessApplicationProperties applicationProperties;

  @Scheduled(cron = "0 * * * * ?")
  public void payTimeout() {
    RLock lock = redissonClient.getLock(applicationProperties.getId() + "easiness-pay-EasinessPayScheduling-payTimeout");

    try {
      if (lock.tryLock(0, 1, TimeUnit.HOURS)) {

        List<EasinessPay> pays = payService.getByPayStatusAndExpirationAtLessThan(PayStatus.DURING, System.currentTimeMillis() - 10 * 60 * 1000);
        for (EasinessPay pay : pays) {
          payBiz.setPayStatusTimeout(pay.getUuid());
        }

        lock.unlock();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      log.warn(e.getMessage(), e);
    }
  }
}
