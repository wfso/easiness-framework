/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.biz;

import cn.ibestcode.easiness.core.annotation.Biz;
import cn.ibestcode.easiness.eventbus.EventBus;
import cn.ibestcode.easiness.pay.domain.EasinessPayCreateVo;
import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.event.PayStatusChangeEvent;
import cn.ibestcode.easiness.pay.exception.EasinessPayException;
import cn.ibestcode.easiness.pay.helper.EasinessPayExtendHelper;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.model.EasinessPayExtend;
import cn.ibestcode.easiness.pay.model.PayStatus;
import cn.ibestcode.easiness.pay.properties.EasinessPayProperties;
import cn.ibestcode.easiness.pay.service.EasinessPayExtendService;
import cn.ibestcode.easiness.pay.service.EasinessPayService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 22:13
 */
@Biz
public class EasinessPayBiz {

  @Autowired
  private EasinessPayService payService;

  @Autowired
  private EasinessPayExtendService extendService;

  @Autowired
  private EasinessPayProperties properties;

  @Autowired
  private EventBus eventBus;

  // region 创建订单

  @Transactional
  public String create(EasinessPay pay, List<EasinessPayExtend> payExtends) {
    // 首先设置订单的状态为：支付中
    pay.setPayStatus(PayStatus.DURING);

    // 持久化支付
    payService.create(pay);

    // 持久化支付扩展
    persistPayExtend(pay, payExtends);

    return pay.getUuid();
  }

  @Transactional
  public String create(EasinessPayCreateVo vo) {
    return create(vo.getEasinessPay(), vo.getPayExtends());
  }

  @Transactional
  public String create(EasinessPay easinessPay) {
    return create(easinessPay, new ArrayList<>());
  }

  @Transactional
  public String create(EasinessPay easinessPay, Map<String, String> map) {
    return create(easinessPay, EasinessPayExtendHelper.getInstanceList(map));
  }


  // endregion

  @Transactional
  public void persistPayExtend(EasinessPay pay, List<EasinessPayExtend> payExtends) {
    for (EasinessPayExtend extend : payExtends) {
      extend.setPayUuid(pay.getUuid());
      extendService.create(extend);
    }
  }

  // region 设置支付的状态

  @Transactional
  public void setPayStatusTimeout(String payUuid) {
    checkPayStatus(payUuid, "[setPayStatusTimeout]", PayStatus.DURING);
    EasinessPay pay = payService.getByUuid(payUuid);
    pay.setPayStatus(PayStatus.TIMEOUT);
    pay.setComplete(true);
    pay.setCompleteAt(System.currentTimeMillis());
    payService.update(pay);
    eventBus.post(new PayStatusChangeEvent(payUuid, pay.getOrderUuid(), PayStatus.TIMEOUT));
  }

  @Transactional
  public void setPayStatusPaid(String payUuid) {
    checkPayStatus(payUuid, "[setPayStatusPaid]", PayStatus.DURING);
    EasinessPay pay = payService.getByUuid(payUuid);
    pay.setPayStatus(PayStatus.PAID);
    payService.update(pay);
    eventBus.post(new PayStatusChangeEvent(payUuid, pay.getOrderUuid(), PayStatus.PAID));
  }

  @Transactional
  public void setPayStatusCancel(String payUuid) {
    checkPayStatus(payUuid, "[setPayStatusCancel]", PayStatus.DURING);
    EasinessPay pay = payService.getByUuid(payUuid);
    pay.setPayStatus(PayStatus.CANCEL);
    pay.setComplete(true);
    pay.setCompleteAt(System.currentTimeMillis());
    payService.update(pay);
    eventBus.post(new PayStatusChangeEvent(payUuid, pay.getOrderUuid(), PayStatus.CANCEL));
  }

  @Transactional
  public void setPayStatusComplete(String payUuid) {
    checkPayStatus(payUuid, "[setPayStatusComplete]", PayStatus.PAID);
    EasinessPay pay = payService.getByUuid(payUuid);
    pay.setPayStatus(PayStatus.COMPLETE);
    pay.setComplete(true);
    pay.setCompleteAt(System.currentTimeMillis());
    payService.update(pay);
    eventBus.post(new PayStatusChangeEvent(payUuid, pay.getOrderUuid(), PayStatus.COMPLETE));
  }

  // endregion


  // region private method
  private boolean checkPayStatus(String payUuid, String message, PayStatus... payStatuses) {
    EasinessPay pay = payService.getByUuid(payUuid);
    if (pay == null) {
      throw new EasinessPayException(new StringBuilder().append(message)
        .append(" EasinessPay not found by uuid[").append(payUuid).append("]").toString());
    }
    return checkPayStatus(pay, message, payStatuses);
  }

  private boolean checkPayStatus(EasinessPay pay, String message, PayStatus... payStatuses) {
    List<PayStatus> statusList = Arrays.asList(payStatuses);
    if (!statusList.contains(pay.getPayStatus())) {
      throw new EasinessPayException(new StringBuilder().append(message)
        .append(" The OrderStatus of EasinessPay was not ").append(payStatuses).toString());
    }
    return true;
  }
  // endregion

  public EasinessPayPassbackParams getPassbackParams() {
    EasinessPayPassbackParams params = new EasinessPayPassbackParams();
    params.setPlatformId(properties.getPlatformId());
    params.setWorkerId(properties.getWorkerId());
    return params;
  }
}
