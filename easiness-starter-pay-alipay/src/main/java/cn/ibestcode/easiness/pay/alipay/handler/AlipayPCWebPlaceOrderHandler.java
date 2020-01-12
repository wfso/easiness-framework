/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.handler;

import cn.ibestcode.easiness.eventbus.EventBus;
import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.pay.alipay.EasinessPayAlipayConstant;
import cn.ibestcode.easiness.pay.alipay.domain.AlipayPCWebPlaceOrderResult;
import cn.ibestcode.easiness.pay.alipay.properties.AlipayPCWebProperties;
import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.domain.PlaceOrderResult;
import cn.ibestcode.easiness.pay.exception.EasinessPayException;
import cn.ibestcode.easiness.pay.handler.AbstractEasinessPayPlaceOrderHandler;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.utils.PriceUtils;
import cn.ibestcode.easiness.spring.utils.CurrentRequestUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/12 20:29
 */
@Component
@Slf4j
public class AlipayPCWebPlaceOrderHandler extends AbstractEasinessPayPlaceOrderHandler {
  @Autowired
  private AlipayPCWebProperties properties;
  @Autowired
  private EventBus eventBus;

  private ObjectMapper objectMapper = new ObjectMapper();

  private AlipayClient alipayClient;

  private SimpleDateFormat expireDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  public String supportType() {
    return EasinessPayAlipayConstant.EASINESS_PAY_TYPE_PC_WEB;
  }

  @Override
  protected PlaceOrderResult placeOrder(EasinessOrder order, EasinessPay pay, EasinessPayPassbackParams passbackParams, Map<String, String> params) {

    if (StringUtils.isNotBlank(pay.getOnlineResultData())) {
      AlipayPCWebPlaceOrderResult result = new AlipayPCWebPlaceOrderResult();
      result.setResponseBody(pay.getOnlineResultData());
      return result;
    }
    // 构造BizContent
    AlipayTradePagePayModel bizModel = new AlipayTradePagePayModel();
    bizModel.setTimeExpire(expireDateFormat.format(new Date(pay.getExpirationAt())));
    bizModel.setOutTradeNo(pay.getUuid());
    bizModel.setProductCode(properties.getProductCode());
    bizModel.setIntegrationType("PCWEB");
    String price = PriceUtils.transformPrice(pay.getPrice());
    bizModel.setTotalAmount(price);
    bizModel.setSubject(order.getOrderName());
    try {
      String passbackParamsJson = objectMapper.writeValueAsString(passbackParams);
      bizModel.setPassbackParams(passbackParamsJson);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    // 构造 AlipayTradePagePayRequest
    AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
    request.setBizModel(bizModel);
    String notifyUrlPrefix = properties.getNotifyUrlPrefix();
    if (notifyUrlPrefix.startsWith("/")) {
      notifyUrlPrefix = notifyUrlPrefix.substring(1);
    }
    if (!notifyUrlPrefix.endsWith("/")) {
      notifyUrlPrefix += "/";
    }
    request.setNotifyUrl(CurrentRequestUtil.getBaseURL() + notifyUrlPrefix + pay.getUuid());
    request.setReturnUrl(properties.getReturnUrl());
    if (StringUtils.isBlank(request.getReturnUrl())) {
      request.setReturnUrl(CurrentRequestUtil.getParameter("returnUrl"));
    }
    if (StringUtils.isBlank(request.getReturnUrl())) {
      request.setReturnUrl(CurrentRequestUtil.getReferer());
    }
    if (StringUtils.isBlank(request.getReturnUrl())) {
      throw new EasinessPayException("ReturnUrlCanNotBeEmpty");
    }
    request.setProdCode(properties.getProductCode());

    try {
      AlipayTradePagePayResponse response = getAlipayClient().pageExecute(request);
      AlipayPCWebPlaceOrderResult result = new AlipayPCWebPlaceOrderResult();
      result.setResponseBody(response.getBody());
      pay.setOnlineUrl(properties.getServerUrl());
      pay.setOnlineParam(objectMapper.writeValueAsString(request));
      pay.setOnlineResultData(result.getResponseBody());
      easinessPayService.update(pay);
      return result;
    } catch (AlipayApiException | JsonProcessingException e) {
      log.warn(e.getMessage(), e);
      e.printStackTrace();
      throw new EasinessPayException("PlaceOrderFailed");
    }
  }

  private AlipayClient getAlipayClient() {
    if (alipayClient == null) {
      alipayClient = new DefaultAlipayClient(
        properties.getServerUrl(),
        properties.getAppId(),
        properties.getPrivateKey(),
        properties.getFormat(),
        properties.getCharset(),
        properties.getPublicKey(),
        properties.getSignType()
      );
    }
    return alipayClient;
  }
}
