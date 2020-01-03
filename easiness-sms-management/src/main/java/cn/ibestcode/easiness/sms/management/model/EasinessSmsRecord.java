/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sms.management.model;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import cn.ibestcode.easiness.core.converter.MapJsonConverter;
import cn.ibestcode.easiness.sendsms.hook.EasinessSmsStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/14 19:14
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel("短信发送记录")
@Table(name = "easiness_sms_record",
  indexes = {
    @Index(columnList = "uuid", name = "easiness_sms_record_uuid", unique = true),
    @Index(columnList = "sendId,senderType", name = "easiness_sms_record_send_id_unique", unique = true),
    @Index(columnList = "sendId", name = "easiness_sms_record_send_id"),
    @Index(columnList = "template", name = "easiness_sms_record_template"),
    @Index(columnList = "createdAt", name = "easiness_sms_record_created_at"),
    @Index(columnList = "phone", name = "easiness_sms_record_phone")
  }
)
public class EasinessSmsRecord extends UuidBaseJpaModel {

  @Column(length = 20)
  @ApiModelProperty("短信的Template")
  private String template;

  @ApiModelProperty("手机号")
  @Column(length = 20)
  private String phone;

  @ApiModelProperty("发送短信的方式")
  @Column(length = 20)
  private String senderType;

  @Lob
  @ApiModelProperty("短信的参数")
  @Convert(converter = MapJsonConverter.class)
  private Map<String, String> vars;

  @ApiModelProperty("发送状态")
  @Column(length = 20)
  @Enumerated(value = EnumType.STRING)
  private EasinessSmsStatus smsStatus;

  @ApiModelProperty("短信平台的发送ID")
  @Column(length = 64)
  private String sendId;

  @Lob
  @JsonIgnore
  @ApiModelProperty(value = "短信平台返回内容", hidden = true)
  private String sendResult;

  @Lob
  @JsonIgnore
  @ApiModelProperty(value = "短信平台回调内容", hidden = true)
  private String hookResult;

  @Column(length = 250)
  @ApiModelProperty("备注")
  private String intro;

  @Column(length = 250)
  @ApiModelProperty("短信内容")
  private String content;

  @ApiModelProperty("完成标识")
  private boolean complete;
}
