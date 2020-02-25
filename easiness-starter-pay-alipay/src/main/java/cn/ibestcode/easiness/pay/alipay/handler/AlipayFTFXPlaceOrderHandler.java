/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.handler;

import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.pay.alipay.EasinessPayAlipayConstant;
import cn.ibestcode.easiness.pay.alipay.properties.AlipayFTFProperties;
import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.exception.EasinessPayException;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.utils.PriceUtils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/12 20:29
 */
@Component
@Slf4j
public class AlipayFTFXPlaceOrderHandler extends AlipayPlaceOrderHandler {
  @Autowired
  private AlipayFTFProperties properties;


  @Override
  public String supportType() {
    return EasinessPayAlipayConstant.EASINESS_PAY_TYPE_FTF_X;
  }

  @Override
  protected AlipayTradePayModel genBizModel(EasinessOrder order,
                                            EasinessPay pay,
                                            EasinessPayPassbackParams passbackParams,
                                            Map<String, String> params) {
    // 构造BizContent
    AlipayTradePayModel bizModel = new AlipayTradePayModel();
    String timeoutExpress = ((pay.getExpirationAt() - pay.getCreatedAt()) / 60000) + "m";
    bizModel.setTimeoutExpress(timeoutExpress);
    bizModel.setOutTradeNo(pay.getUuid());
    bizModel.setScene("bar_code");
    bizModel.setAuthCode(params.get("authCode"));
    if (params.containsKey("operatorId")) {
      bizModel.setOperatorId(params.get("operatorId"));
    }
    if (params.containsKey("storeId")) {
      bizModel.setStoreId(params.get("storeId"));
    }
    if (params.containsKey("terminalId")) {
      bizModel.setTerminalId(params.get("terminalId"));
    }
    bizModel.setProductCode(properties.getProductCode());
    String price = PriceUtils.transformPrice(pay.getPrice());
    bizModel.setTotalAmount(price);
    bizModel.setSubject(order.getOrderName());
    bizModel.setBody(order.getOrderInfo());

    return bizModel;
  }

  protected void changePayStatus(EasinessOrder order, EasinessPay pay, AlipayResponse response) {
    String code = response.getCode();
    if ("10000".equals(code)) {
      easinessPayBiz.setPayStatusPaid(pay.getUuid());
    }
  }

  @Override
  protected boolean requireReturnUrl() {
    return false;
  }

  @Override
  protected AlipayResponse executeRequest(AlipayRequest request) {
    try {
      return getAlipayClient(properties).execute(request);
    } catch (AlipayApiException e) {
      e.printStackTrace();
      log.warn(e.getMessage(), e);
      throw new EasinessPayException("PlaceOrderFailed");
    }
  }

  @Override
  protected AlipayTradePayRequest newAlipayRequest() {
    return new AlipayTradePayRequest();
  }

  @Override
  protected AlipayFTFProperties getAlipayProperties() {
    return properties;
  }
}
