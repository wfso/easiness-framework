/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.wechat.handler;

import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.domain.PlaceOrderResult;
import cn.ibestcode.easiness.pay.exception.EasinessPayException;
import cn.ibestcode.easiness.pay.handler.AbstractEasinessPayPlaceOrderHandler;
import cn.ibestcode.easiness.pay.model.EasinessPay;
import cn.ibestcode.easiness.pay.wechat.domain.PlaceOrderParams;
import cn.ibestcode.easiness.pay.wechat.domain.WechatPlaceOrderResult;
import cn.ibestcode.easiness.pay.wechat.properties.WechatProperties;
import cn.ibestcode.easiness.pay.wechat.utils.SignUtil;
import cn.ibestcode.easiness.pay.wechat.utils.PayParamsUtil;
import cn.ibestcode.easiness.spring.utils.CurrentRequestUtil;
import cn.ibestcode.easiness.spring.utils.IpUtil;
import cn.ibestcode.easiness.utils.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/28 18:52
 */
@Slf4j
public abstract class WechatPlaceOrderHandler extends AbstractEasinessPayPlaceOrderHandler {
  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected XmlMapper xmlMapper;

  @Autowired
  protected RestTemplate restTemplate;

  @Override
  protected PlaceOrderResult placeOrder(EasinessOrder order,
                                        EasinessPay pay,
                                        EasinessPayPassbackParams passbackParams,
                                        Map<String, String> params) {

    if (StringUtils.isNotBlank(pay.getOnlineResultData())) {
      WechatPlaceOrderResult result = null;
      try {
        result = xmlMapper.readValue(pay.getOnlineResultData(), WechatPlaceOrderResult.class);
        setResponseBody(result);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return result;
    }

    WechatProperties properties = getProperties();

    PlaceOrderParams orderParams = genPlaceOrderParams(order, pay, passbackParams, params);

    orderParams.setAppid(properties.getAppId());
    orderParams.setMchId(properties.getMchId());
    orderParams.setNonceStr(RandomUtil.generateUnseparatedUuid());
    orderParams.setOutTradeNo(pay.getUuid());
    orderParams.setSpbillCreateIp(IpUtil.getClientIpAddress());
    orderParams.setTotalFee(String.valueOf(pay.getPrice()));
    orderParams.setBody(order.getOrderName());
    orderParams.setFeeType(pay.getCurrency());
    orderParams.setTradeType(properties.getTradeType());
    orderParams.setSignType(properties.getSignType());

    String notifyUrlPrefix = properties.getNotifyUrlPrefix();
    if (notifyUrlPrefix.startsWith("/")) {
      notifyUrlPrefix = notifyUrlPrefix.substring(1);
    }
    if (!notifyUrlPrefix.endsWith("/")) {
      notifyUrlPrefix += "/";
    }
    orderParams.setNotifyUrl(CurrentRequestUtil.getBaseURL() + notifyUrlPrefix + pay.getUuid());

    try {
      orderParams.setAttach(objectMapper.writeValueAsString(passbackParams));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    orderParams.setSign(SignUtil.sign(orderParams, properties.getAppKey(), orderParams.getSignType()).toUpperCase());

    String placeOrderXml = PayParamsUtil.genXmlParams(orderParams);
    String resultStr = restTemplate.postForObject(properties.getPlaceOrderUrl(), placeOrderXml, String.class);

    WechatPlaceOrderResult result = null;
    try {
      TreeMap<String, String> map = xmlMapper.readValue(resultStr, TreeMap.class);

      result = xmlMapper.readValue(resultStr, WechatPlaceOrderResult.class);

      String sign = map.remove("sign");
      String signType = map.remove("sign_type");
      if (StringUtils.isBlank(signType)) {
        signType = orderParams.getSignType();
      }

      String signVerified = SignUtil.sign(map, properties.getAppKey(), signType);

      if (result.isSucceed() && sign != null && sign.equalsIgnoreCase(signVerified)) {
        pay.setOnlineUrl(properties.getPlaceOrderUrl());
        pay.setOnlineParam(placeOrderXml);
        pay.setOnlineResultData(resultStr);
        easinessPayService.update(pay);
        setResponseBody(result);
        return result;
      } else {
        easinessPayBiz.setPayStatusCancel(pay.getUuid());
        log.warn(placeOrderXml);
        log.warn(result.toJSON());
        log.warn(resultStr);
        log.warn(sign);
        log.warn(signVerified);
        throw new EasinessPayException("PlaceOrderFiled");
      }
    } catch (IOException e) {
      easinessPayBiz.setPayStatusCancel(pay.getUuid());
      e.printStackTrace();
      log.warn(e.getMessage(), e);
      throw new EasinessPayException("PlaceOrderFiled");
    }
  }

  protected abstract PlaceOrderParams genPlaceOrderParams(EasinessOrder order, EasinessPay pay, EasinessPayPassbackParams passbackParams, Map<String, String> params);

  protected abstract void setResponseBody(WechatPlaceOrderResult result) throws IOException;


  protected abstract WechatProperties getProperties();

}
