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
import cn.ibestcode.easiness.pay.wechat.domain.WechatPlaceOrderResult;
import cn.ibestcode.easiness.pay.wechat.properties.WechatProperties;
import cn.ibestcode.easiness.pay.wechat.utils.SignUtil;
import cn.ibestcode.easiness.spring.utils.CurrentRequestUtil;
import cn.ibestcode.easiness.spring.utils.IpUtil;
import cn.ibestcode.easiness.utils.MacUtil;
import cn.ibestcode.easiness.utils.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
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
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final XmlMapper xmlMapper = new XmlMapper();

  @Autowired
  private RestTemplate restTemplate;

  @Override
  protected PlaceOrderResult placeOrder(EasinessOrder order,
                                        EasinessPay pay,
                                        EasinessPayPassbackParams passbackParams,
                                        Map<String, String> params) {

    if (StringUtils.isNotBlank(pay.getOnlineResultData())) {
      WechatPlaceOrderResult result = null;
      try {
        result = xmlMapper.readValue(pay.getOnlineResultData(), WechatPlaceOrderResult.class);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return result;
    }

    WechatProperties properties = getProperties();

    PlaceOrderParams orderParams = new PlaceOrderParams();
    orderParams.setAppId(properties.getAppId());
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

    orderParams.setSign(MacUtil.hmacSha256Hex(orderParams.getSginStr(properties.getAppKey()), properties.getAppKey()).toUpperCase());

    String placeOrderXml = orderParams.getPlaceOrderXml();
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

      if (result.isSucceed() && signVerified.equalsIgnoreCase(sign)) {
        pay.setOnlineUrl(properties.getPlaceOrderUrl());
        pay.setOnlineParam(placeOrderXml);
        pay.setOnlineResultData(resultStr);
        easinessPayService.update(pay);
        setResponseBody(result);
        return result;
      } else {
        easinessPayBiz.setPayStatusCancel(pay.getUuid());
        log.warn(orderParams.getSginStr(properties.getAppKey()));
        log.warn(orderParams.getPlaceOrderXml());
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

  protected abstract void setResponseBody(WechatPlaceOrderResult result);

  @Getter
  @Setter
  public static class PlaceOrderParams {
    @ApiModelProperty("应用APPID")
    private String appId;
    @ApiModelProperty("商户号")
    private String mchId;
    @ApiModelProperty("随机字符串")
    private String nonceStr;
    @ApiModelProperty("签名")
    private String sign;
    @ApiModelProperty("签名类型")
    private String signType;
    @ApiModelProperty("商品描述")
    private String body;
    @ApiModelProperty("商户订单号")
    private String outTradeNo;
    @ApiModelProperty("标价金额")
    private String totalFee;
    @ApiModelProperty("终端IP")
    private String spbillCreateIp;
    @ApiModelProperty("通知地址")
    private String notifyUrl;
    @ApiModelProperty("交易类型")
    private String tradeType;
    @ApiModelProperty("附加数据")
    private String attach;
    @ApiModelProperty("标价币种")
    private String feeType;

    public String getPlaceOrderXml() {
      StringBuilder sb = new StringBuilder();
      sb.append("<xml>");
      if (StringUtils.isNotBlank(appId)) {
        sb.append("<appid>").append(appId).append("</appid>");
      }
      if (StringUtils.isNotBlank(attach)) {
        sb.append("<attach>").append(attach).append("</attach>");
      }
      if (StringUtils.isNotBlank(body)) {
        sb.append("<body>").append(body).append("</body>");
      }
      if (StringUtils.isNotBlank(mchId)) {
        sb.append("<mch_id>").append(mchId).append("</mch_id>");
      }
      if (StringUtils.isNotBlank(nonceStr)) {
        sb.append("<nonce_str>").append(nonceStr).append("</nonce_str>");
      }
      if (StringUtils.isNotBlank(notifyUrl)) {
        sb.append("<notify_url>").append(notifyUrl).append("</notify_url>");
      }
      if (StringUtils.isNotBlank(outTradeNo)) {
        sb.append("<out_trade_no>").append(outTradeNo).append("</out_trade_no>");
      }
      if (StringUtils.isNotBlank(spbillCreateIp)) {
        sb.append("<spbill_create_ip>").append(spbillCreateIp).append("</spbill_create_ip>");
      }
      if (StringUtils.isNotBlank(totalFee)) {
        sb.append("<total_fee>").append(totalFee).append("</total_fee>");
      }
      if (StringUtils.isNotBlank(tradeType)) {
        sb.append("<trade_type>").append(tradeType).append("</trade_type>");
      }
      if (StringUtils.isNotBlank(signType)) {
        sb.append("<sign_type>").append(signType).append("</sign_type>");
      }
      if (StringUtils.isNotBlank(feeType)) {
        sb.append("<fee_type>").append(feeType).append("</fee_type>");
      }
      if (StringUtils.isNotBlank(sign)) {
        sb.append("<sign>").append(sign).append("</sign>");
      }
      sb.append("</xml>");
      return sb.toString();
    }

    public String getSginStr(String key) {
      TreeMap<String, String> treeMap = new TreeMap<>();
      treeMap.put("appid", appId);
      treeMap.put("mchId", mchId);
      treeMap.put("nonceStr", nonceStr);
      treeMap.put("body", body);
      treeMap.put("signType", signType);
      treeMap.put("outTradeNo", outTradeNo);
      treeMap.put("totalFee", totalFee);
      treeMap.put("spbillCreateIp", spbillCreateIp);
      treeMap.put("notifyUrl", notifyUrl);
      treeMap.put("tradeType", tradeType);
      treeMap.put("attach", attach);
      treeMap.put("feeType", feeType);
      StringBuilder sb = new StringBuilder();

      for (Map.Entry<String, String> entry : treeMap.entrySet()) {
        if (StringUtils.isNotBlank(entry.getValue())) {
          sb.append(entry.getKey().replaceAll("([^\\_])([A-Z])", "$1_$2").toLowerCase().replace(".", "`.`"))
            .append("=")
            .append(entry.getValue())
            .append("&");
        }
      }
      sb.append("key").append("=").append(key);
      return sb.toString();
    }
  }

  protected abstract WechatProperties getProperties();

}
