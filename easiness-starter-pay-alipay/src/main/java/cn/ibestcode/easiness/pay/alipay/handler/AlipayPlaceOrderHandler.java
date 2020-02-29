/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.handler;

import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.pay.alipay.domain.AlipayPlaceOrderResult;
import cn.ibestcode.easiness.pay.alipay.properties.AlipayProperties;
import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.domain.PlaceOrderResult;
import cn.ibestcode.easiness.pay.exception.EasinessPayException;
import cn.ibestcode.easiness.pay.handler.AbstractEasinessPayPlaceOrderHandler;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.spring.utils.CurrentRequestUtil;
import com.alipay.api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/12 20:29
 */
@Slf4j
public abstract class AlipayPlaceOrderHandler extends AbstractEasinessPayPlaceOrderHandler {

  private Map<String, AlipayClient> alipayClientMap = new HashMap<>();

  protected ObjectMapper objectMapper = new ObjectMapper();

  protected SimpleDateFormat expireDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  protected PlaceOrderResult placeOrder(EasinessOrder order, EasinessPay pay, EasinessPayPassbackParams passbackParams, Map<String, String> params) {

    if (StringUtils.isNotBlank(pay.getOnlineResultData())) {
      AlipayPlaceOrderResult result = new AlipayPlaceOrderResult();
      result.setResponseBody(pay.getOnlineResultData());
      return result;
    }

    AlipayRequest request = newAlipayRequest();

    setNotifyUrl(request, pay);

    if (requireReturnUrl()) {
      setReturnUrl(request);
    }

    request.setProdCode(getAlipayProperties().getProductCode());

    request.setBizModel(genBizModel(order, pay, passbackParams, params));

    try {
      AlipayPlaceOrderResult result = executeRequest(request);
      if (result.isSucceed()) {
        pay.setOnlineUrl(getAlipayProperties().getServerUrl());
        pay.setOnlineParam(objectMapper.writeValueAsString(request));
        pay.setOnlineResultData(result.getResponseBody());
        easinessPayService.update(pay);
        return result;
      } else {
        easinessPayBiz.setPayStatusCancel(pay.getUuid());
        log.warn(objectMapper.writeValueAsString(request));
        log.warn(result.getResponseBody());
        throw new EasinessPayException("PlaceOrderFailed");
      }
    } catch (JsonProcessingException e) {
      log.warn(e.getMessage(), e);
      e.printStackTrace();
      throw new EasinessPayException("PlaceOrderFailed");
    }
  }

  protected void setNotifyUrl(AlipayRequest request, EasinessPay pay) {
    AlipayProperties properties = getAlipayProperties();

    String notifyUrlPrefix = properties.getNotifyUrlPrefix();
    if (notifyUrlPrefix.startsWith("/")) {
      notifyUrlPrefix = notifyUrlPrefix.substring(1);
    }
    if (!notifyUrlPrefix.endsWith("/")) {
      notifyUrlPrefix += "/";
    }
    request.setNotifyUrl(CurrentRequestUtil.getBaseURL() + notifyUrlPrefix + pay.getUuid());
  }

  protected void setReturnUrl(AlipayRequest request) {
    AlipayProperties properties = getAlipayProperties();
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
  }


  protected abstract AlipayObject genBizModel(EasinessOrder order, EasinessPay pay, EasinessPayPassbackParams passbackParams, Map<String, String> params);

  protected abstract boolean requireReturnUrl();

  protected abstract AlipayPlaceOrderResult executeRequest(AlipayRequest request);

  protected abstract AlipayRequest newAlipayRequest();

  protected abstract AlipayProperties getAlipayProperties();

  protected AlipayClient getAlipayClient(AlipayProperties properties) {
    String propertiesClassName = properties.getClass().getName();
    if (alipayClientMap.containsKey(propertiesClassName)) {
      return alipayClientMap.get(propertiesClassName);
    }
    AlipayClient alipayClient = new DefaultAlipayClient(
      properties.getServerUrl(),
      properties.getAppId(),
      properties.getPrivateKey(),
      properties.getFormat(),
      properties.getCharset(),
      properties.getPublicKey(),
      properties.getSignType()
    );
    alipayClientMap.put(propertiesClassName, alipayClient);

    return alipayClient;
  }
}
