/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.pay.alipay.domain;

import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.domain.PayAsyncNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/2/1 20:29
 */
@Setter
@Getter
@ToString
@ApiModel("支付宝支付异步通知对象-基类")
public class AlipayAsyncNotification implements PayAsyncNotification, Serializable {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public AlipayAsyncNotification() {
    // 默认值
    setCharset("utf-8");
    setPassbackParams("{}");
  }

  public AlipayAsyncNotification(Map<String, String> params) {
    this();

    setNotifyTime(params.get("notify_time"));
    setNotifyType(params.get("notify_type"));
    setNotifyId(params.get("notify_id"));
    setSignType(params.get("sign_type"));
    setSign(params.get("sign"));

    setTradeNo(params.get("trade_no"));
    setAppId(params.get("app_id"));
    setOutTradeNo(params.get("out_trade_no"));
    setOutBizNo(params.get("out_biz_no"));
    setBuyerId(params.get("buyer_id"));
    setSellerId(params.get("seller_id"));
    setTradeStatus(params.get("trade_status"));
    setTotalAmount(params.get("total_amount"));
    setReceiptAmount(params.get("receipt_amount"));
    setInvoiceAmount(params.get("invoice_amount"));
    setBuyerPayAmount(params.get("buyer_pay_amount"));
    setPointAmount(params.get("point_amount"));
    setRefundFee(params.get("refund_fee"));
    setSubject(params.get("subject"));
    setBody(params.get("body"));
    setGmtCreate(params.get("gmt_create"));//有
    setGmtPayment(params.get("gmt_payment"));//有
    setGmtRefund(params.get("gmt_refund"));//有
    setGmtClose(params.get("gmt_close"));//有
    setFundBillList(params.get("fund_bill_list"));//有
  }

  public static AlipayAsyncNotification getInstance(String json) throws IOException {
    AlipayAsyncNotification notification = objectMapper.readValue(json, AlipayAsyncNotification.class);
    notification.parsePassbackParams();
    return notification;
  }

  // region 支付通知参数

  @ApiModelProperty("通知时间")
  private String notifyTime;

  @ApiModelProperty("通知类型")
  private String notifyType;

  @ApiModelProperty("通知校验ID")
  private String notifyId;

  @ApiModelProperty("编码格式")
  private String charset;

  @ApiModelProperty("签名类型")
  private String signType;

  @ApiModelProperty("签名")
  private String sign;

  @ApiModelProperty("支付宝交易号")
  private String tradeNo;

  @ApiModelProperty("开发者的app_id")
  private String appId;

  @ApiModelProperty("商户订单号")
  private String outTradeNo;

  @ApiModelProperty("商户业务号")
  private String outBizNo;

  @ApiModelProperty("买家支付宝用户号")
  private String buyerId;

  @ApiModelProperty("卖家支付宝用户号")
  private String sellerId;

  @ApiModelProperty("交易状态")
  private String tradeStatus;

  @ApiModelProperty("订单金额")
  private String totalAmount;

  @ApiModelProperty("实收金额")
  private String receiptAmount;

  @ApiModelProperty("开票金额")
  private String invoiceAmount;

  @ApiModelProperty("付款金额")
  private String buyerPayAmount;

  @ApiModelProperty("集分宝金额")
  private String pointAmount;

  @ApiModelProperty("总退款金额")
  private String refundFee;

  @ApiModelProperty("订单标题")
  private String subject;

  @ApiModelProperty("商品描述")
  private String body;

  @ApiModelProperty("交易创建时间")
  private String gmtCreate;

  @ApiModelProperty("交易付款时间")
  private String gmtPayment;

  @ApiModelProperty("交易退款时间")
  private String gmtRefund;

  @ApiModelProperty("交易结束时间")
  private String gmtClose;

  @ApiModelProperty("支付金额信息")
  private String fundBillList;

  @ApiModelProperty("回传参数")
  private String passbackParams;

  // endregion


  // region Yioks框架参数

  @ApiModelProperty("平台标识")
  private String platformId;

  @ApiModelProperty("服务器标识")
  private String workerId;

  // endregion


  @Override
  public String getTradeId() {
    return tradeNo;
  }

  @Override
  public String getOutTradeId() {
    return outTradeNo;
  }

  @Override
  public String getPlatformId() {
    if (StringUtils.isBlank(platformId)) {
      parsePassbackParams();
    }
    return platformId;
  }

  @Override
  public String getWorkerId() {
    if (StringUtils.isBlank(workerId)) {
      parsePassbackParams();
    }
    return workerId;
  }

  @Override
  public boolean isSucceed() {
    return "TRADE_SUCCESS".equalsIgnoreCase(getTradeStatus()) ||
      "TRADE_FINISHED".equalsIgnoreCase(getTradeStatus());
  }

  @Override
  public boolean isClosed() {
    return "TRADE_CLOSED".equalsIgnoreCase(getTradeStatus());
  }

  @Override
  public String toJSON() {
    try {
      return objectMapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void parsePassbackParams() {
    if (StringUtils.isBlank(passbackParams)) {
      return;
    }
    try {
      EasinessPayPassbackParams params = objectMapper.readValue(passbackParams, EasinessPayPassbackParams.class);
      this.platformId = params.getPlatformId();
      this.workerId = params.getWorkerId();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
