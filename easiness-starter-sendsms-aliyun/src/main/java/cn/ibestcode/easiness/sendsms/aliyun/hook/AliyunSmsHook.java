/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.aliyun.hook;

import cn.ibestcode.easiness.sendsms.aliyun.AliyunSendSmsConstant;
import cn.ibestcode.easiness.sendsms.exception.SendSmsException;
import cn.ibestcode.easiness.sendsms.hook.EasinessSmsHook;
import cn.ibestcode.easiness.sendsms.hook.EasinessSmsStatus;
import cn.ibestcode.easiness.sendsms.hook.SmsHookResult;
import cn.ibestcode.easiness.sendsms.hook.SmsHookResultItem;
import cn.ibestcode.easiness.spring.utils.CurrentRequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/18 20:26
 */
@Slf4j
@Component
public class AliyunSmsHook implements EasinessSmsHook {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String supportType() {
    return AliyunSendSmsConstant.ALIYUN_TYPE;
  }

  @Override
  public SmsHookResult hook() {
    String body = CurrentRequestUtil.getBody();
    try {
      JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, AliyunSmsHookResultItem.class);
      List<AliyunSmsHookResultItem> items = objectMapper.readValue(body, javaType);

      if (items == null || items.size() == 0) {
        return new AliyunSmsHookResult();
      }

      return new AliyunSmsHookResult(items);
    } catch (IOException e) {
      log.warn(e.getMessage(), e);
      throw new SendSmsException("ParseJSONFail", e);
    }
  }


  @Getter
  @Setter
  protected static class AliyunSmsHookResult implements SmsHookResult, Serializable {

    private List<SmsHookResultItem> items = new ArrayList<>();

    public AliyunSmsHookResult() {
    }

    public AliyunSmsHookResult(List<AliyunSmsHookResultItem> items) {
      for (SmsHookResultItem item : items) {
        addItem(item);
      }
    }

    public void addItem(SmsHookResultItem item) {
      items.add(item);
    }

    @Override
    public String getResponse() {
      return "{\"code\":0}";
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

  @Getter
  @Setter
  protected static class AliyunSmsHookResultItem implements SmsHookResultItem, Serializable {
    private String phone_number;
    private String send_time;
    private String report_time;
    private String success;
    private String err_code;
    private String err_msg;
    private String sms_size;
    private String biz_id;
    private String out_id;

    @Override
    public String getSendId() {
      return getBiz_id();
    }

    @Override
    public String getPhone() {
      return getPhone_number();
    }

    @Override
    public String getSenderType() {
      return AliyunSendSmsConstant.ALIYUN_TYPE;
    }

    @Override
    public EasinessSmsStatus getStatus() {
      if (getSuccess().equalsIgnoreCase("true") && getErr_code().equalsIgnoreCase("DELIVERED")) {
        return EasinessSmsStatus.SUCCESS;
      }
      return EasinessSmsStatus.FAILED;
    }

    @Override
    public String getIntro() {
      return getErr_code();
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
