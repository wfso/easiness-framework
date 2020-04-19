/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.submail.hook;

import cn.ibestcode.easiness.sendsms.exception.SendSmsException;
import cn.ibestcode.easiness.sendsms.hook.EasinessSmsHook;
import cn.ibestcode.easiness.sendsms.hook.EasinessSmsStatus;
import cn.ibestcode.easiness.sendsms.hook.SmsHookResult;
import cn.ibestcode.easiness.sendsms.hook.SmsHookResultItem;
import cn.ibestcode.easiness.sendsms.submail.SubmailSendSmsConstant;
import cn.ibestcode.easiness.sendsms.submail.properties.SubmailSmsProperties;
import cn.ibestcode.easiness.spring.utils.CurrentRequestUtil;
import cn.ibestcode.easiness.utils.DigestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/18 20:11
 */
@Slf4j
@Component
public class SubmailSmsHook implements EasinessSmsHook {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private SubmailSmsProperties smsProperties;

  @Override
  public String supportType() {
    return SubmailSendSmsConstant.SUBMAIL_TYPE;
  }

  @Override
  public SmsHookResult hook() {
    Map<String, String> requestParams = CurrentRequestUtil.getParameters();
    SubmailSmsHookResultItem item = new SubmailSmsHookResultItem(requestParams);

    if (!smsProperties.getAppId().equals(item.getApp())) {
      return new SubmailSmsHookResult();
    }

    if (StringUtils.isNotEmpty(smsProperties.getHookKey()) && StringUtils.isNotEmpty(item.getToken()) && StringUtils.isNotEmpty(item.getSignature())) {
      if (DigestUtil.md5Hex(item.getToken() + smsProperties.getHookKey()).equalsIgnoreCase(item.getSignature())) {
        return new SubmailSmsHookResult(item);
      } else {
        log.warn("EasinessSmsHook of type [{}] signature error", supportType());
        throw new RuntimeException("SubmailSmsHookSignatureError");
      }
    }
    log.warn("EasinessSmsHook parameters error");
    throw new RuntimeException("SubmailSmsHookParametersError");
  }

  @Getter
  @Setter
  protected class SubmailSmsHookResult implements SmsHookResult, Serializable {

    List<SmsHookResultItem> items = new ArrayList<>();

    public SubmailSmsHookResult() {
    }

    public SubmailSmsHookResult(SubmailSmsHookResultItem item) {
      items.add(item);
    }

    @Override
    public String toJSON() {
      try {
        return objectMapper.writeValueAsString(this);
      } catch (JsonProcessingException e) {
        log.warn(e.getMessage(), e);
        throw new SendSmsException("ConvertJSONFail", e);
      }
    }

    @Override
    public String getResponse() {
      return "";
    }

    @Override
    public String toString() {
      return toJSON();
    }
  }

  @Getter
  @Setter
  protected class SubmailSmsHookResultItem implements SmsHookResultItem, Serializable {

    private String events;
    private String address;
    private String app;
    private String sendId;
    private String timestamp;
    private String token;
    private String signature;
    private String report;

    public SubmailSmsHookResultItem(Map<String, String> params) {
      this.events = params.get("events");
      this.address = params.get("address");
      this.app = params.get("app");
      this.sendId = params.get("send_id");
      this.timestamp = params.get("timestamp");
      this.token = params.get("token");
      this.signature = params.get("signature");
      this.report = params.get("report");
    }

    @Override
    public String getPhone() {
      return getAddress();
    }

    @Override
    public String getSenderType() {
      return SubmailSendSmsConstant.SUBMAIL_TYPE;
    }

    @Override
    public EasinessSmsStatus getStatus() {
      if (events == null) {
        return EasinessSmsStatus.FAILED;
      }
      switch (events) {
        case "delivered":
          return EasinessSmsStatus.SUCCESS;
        case "dropped":
        case "unkown":
          return EasinessSmsStatus.FAILED;
        case "request":
        case "sending":
          return EasinessSmsStatus.SENDING;
      }
      return EasinessSmsStatus.FAILED;
    }

    @Override
    public String getIntro() {
      return getReport();
    }


    @Override
    public String toJSON() {
      try {
        return objectMapper.writeValueAsString(this);
      } catch (JsonProcessingException e) {
        log.warn(e.getMessage(), e);
        throw new SendSmsException("ConvertJSONFail", e);
      }
    }

    @Override
    public String toString() {
      return toJSON();
    }
  }
}
