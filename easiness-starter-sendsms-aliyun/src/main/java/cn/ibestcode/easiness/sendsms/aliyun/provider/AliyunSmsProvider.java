/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.aliyun.provider;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.sendsms.aliyun.AliyunSendSmsConstant;
import cn.ibestcode.easiness.sendsms.aliyun.properties.AliyunSmsProperties;
import cn.ibestcode.easiness.sendsms.exception.SendSmsException;
import cn.ibestcode.easiness.sendsms.provider.SmsProvider;
import cn.ibestcode.easiness.sendsms.sender.SmsSender;
import cn.ibestcode.easiness.sendsms.sender.SmsSenderResult;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Slf4j
@Component
public class AliyunSmsProvider implements SmsProvider {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired(required = false)
  private EasinessConfiguration configuration;

  @Autowired
  private AliyunSmsProperties smsProperties;

  private SmsSender smsSender;

  @Override
  public SmsSender getSender() {
    if (smsSender == null) {
      String accessKeyId = null, accessKeySecret = null, signName = null, domain = null, version = null, action = null;
      if (configuration != null) {
        accessKeyId = configuration.getConfig("easiness.sms.aliyun.accessKeyId");
        accessKeySecret = configuration.getConfig("easiness.sms.aliyun.accessKeySecret");
        signName = configuration.getConfig("easiness.sms.aliyun.signName");
        domain = configuration.getConfig("easiness.sms.aliyun.domain");
        version = configuration.getConfig("easiness.sms.aliyun.version");
        action = configuration.getConfig("easiness.sms.aliyun.action");
      }

      if (StringUtils.isEmpty(accessKeyId)) {
        accessKeyId = smsProperties.getAccessKeyId();
        Assert.isTrue(!StringUtils.isEmpty(accessKeyId), "请配置阿里云短信");
      }

      if (StringUtils.isEmpty(accessKeySecret)) {
        accessKeySecret = smsProperties.getAccessKeySecret();
        Assert.isTrue(!StringUtils.isEmpty(accessKeySecret), "请配置阿里云短信");
      }

      if (StringUtils.isEmpty(signName)) {
        signName = smsProperties.getSignName();
        Assert.isTrue(!StringUtils.isEmpty(signName), "请配置阿里云短信");
      }

      if (StringUtils.isEmpty(domain)) {
        domain = smsProperties.getDomain();
        Assert.isTrue(!StringUtils.isEmpty(domain), "请配置阿里云短信");
      }

      if (StringUtils.isEmpty(version)) {
        version = smsProperties.getVersion();
        Assert.isTrue(!StringUtils.isEmpty(version), "请配置阿里云短信");
      }

      if (StringUtils.isEmpty(action)) {
        action = smsProperties.getAction();
        Assert.isTrue(!StringUtils.isEmpty(action), "请配置阿里云短信");
      }
      smsSender = new AliyunSmsSender(
        accessKeyId,
        accessKeySecret,
        signName,
        domain,
        version,
        action
      );
    }
    return smsSender;
  }

  @Override
  public void clear() {
    smsSender = null;
  }

  @Override
  public String getType() {
    return AliyunSendSmsConstant.ALIYUN_TYPE;
  }

  private static class AliyunSmsSender implements SmsSender {
    private final IAcsClient client;
    private final String signName;
    private final String domain;
    private final String version;
    private final String action;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public AliyunSmsSender(String accessKeyId, String accessKeySecret, String signName, String domain, String version, String action) {
      DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
      this.signName = signName;
      this.domain = domain;
      this.version = version;
      this.action = action;
      this.client = new DefaultAcsClient(profile);
    }

    @Override
    public SmsSenderResult sendSms(String phone, String template, Map<String, String> vars) {
      CommonRequest request = new CommonRequest();
      request.setMethod(MethodType.POST);
      request.setDomain(this.domain);
      request.setVersion(this.version);
      request.setAction(this.action);
      request.putQueryParameter("PhoneNumbers", phone);
      request.putQueryParameter("SignName", signName);
      request.putQueryParameter("TemplateCode", template);
      try {
        request.putQueryParameter("TemplateParam", objectMapper.writeValueAsString(vars));
      } catch (JsonProcessingException e) {
        log.warn(e.getMessage(), e);
        throw new SendSmsException("ConvertJSONFail", e);
      }
      try {
        CommonResponse response = client.getCommonResponse(request);
        // System.out.println(response.getData());
        try {
          return objectMapper.readValue(response.getData(), AliyunSmsResult.class);
        } catch (IOException e) {
          log.warn(e.getMessage(), e);
          throw new SendSmsException("ParseJSONFail", e);
        }
      } catch (ServerException e) {
        logger.warn(e.getMessage(), e);
        throw new SendSmsException("ServerException", e);
      } catch (ClientException e) {
        logger.warn(e.getMessage(), e);
        throw new SendSmsException("ClientException", e);
      }
    }
  }

  @Getter
  @Setter
  private static class AliyunSmsResult implements SmsSenderResult {

    private String BizId;
    private String Code;
    private String Message;
    private String RequestId;

    @Override
    public String getId() {
      return getBizId();
    }

    @Override
    public boolean isSuccess() {
      return "OK".equalsIgnoreCase(getCode());
    }

    @Override
    public String getSenderType() {
      return AliyunSendSmsConstant.ALIYUN_TYPE;
    }

    @Override
    public String toString() {
      return toJSON();
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
  }
}
