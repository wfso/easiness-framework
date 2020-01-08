/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.event;

import cn.ibestcode.easiness.pay.model.PayStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 20:15
 */
@Getter
@Setter
@ToString
public class PayStatusChangeEvent implements Serializable {
  public PayStatusChangeEvent(String payUuid, String orderUuid, PayStatus payStatus) {
    this.payUuid = payUuid;
    this.orderUuid = orderUuid;
    this.payStatus = payStatus;
  }

  private String payUuid;
  private String orderUuid;
  private PayStatus payStatus;
}
