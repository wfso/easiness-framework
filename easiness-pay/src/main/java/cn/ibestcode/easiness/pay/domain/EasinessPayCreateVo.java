/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.domain;

import cn.ibestcode.easiness.pay.helper.EasinessPayExtendHelper;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.model.EasinessPayExtend;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/07 21:13
 */
@Getter
@Setter
@ToString
@ApiModel("添加支付的VO")
public class EasinessPayCreateVo implements Serializable {

  public EasinessPayCreateVo() {
  }

  public EasinessPayCreateVo(EasinessPay easinessPay) {
    this.easinessPay = easinessPay;
  }

  public EasinessPayCreateVo(EasinessPay easinessPay, List<EasinessPayExtend> payExtends) {
    this(easinessPay);
    this.payExtends = payExtends;
  }

  public EasinessPayCreateVo(EasinessPay easinessPay, Map<String, String> map) {
    this(easinessPay, EasinessPayExtendHelper.getInstanceList(map));
  }

  @ApiModelProperty("支付对象")
  @NotNull
  private EasinessPay easinessPay;

  @ApiModelProperty("支付对象的扩展列表")
  private List<EasinessPayExtend> payExtends;
}
