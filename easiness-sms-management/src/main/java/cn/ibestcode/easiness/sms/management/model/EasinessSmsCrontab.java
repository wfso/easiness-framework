package cn.ibestcode.easiness.sms.management.model;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import cn.ibestcode.easiness.core.converter.MapJsonConverter;
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
@Table(name = "easiness_sms_crontab",
  indexes = {
    @Index(columnList = "uuid", name = "easiness_sms_crontab_uuid", unique = true),
    @Index(columnList = "sendId", name = "easiness_sms_crontab_send_id", unique = true),
    @Index(columnList = "template", name = "easiness_sms_crontab_template"),
    @Index(columnList = "phone", name = "easiness_sms_crontab_phone"),
    @Index(columnList = "sendAt", name = "easiness_sms_crontab_send_at")
  }
)
@Getter
@Setter
@ApiModel(description = "短信")
@ToString(callSuper = true)
public class EasinessSmsCrontab extends UuidBaseJpaModel {

  @ApiModelProperty("定时发送时间")
  private long sendAt;

  @ApiModelProperty("短信模板")
  @Column(length = 20)
  private String template;

  @ApiModelProperty("手机号")
  @Column(length = 20)
  private String phone;

  @ApiModelProperty("分组(批次)-同一批次发送的短信，group相同 - 推荐使用uuid")
  @Column(length = 64)
  private String smsGroup;

  @ApiModelProperty("短信参数")
  @Lob
  @Convert(converter = MapJsonConverter.class)
  private Map<String, String> vars;

  @ApiModelProperty("发送状态")
  @Column(length = 20)
  @Enumerated(value = EnumType.STRING)
  private EasinessSmsStatus smsStatus;

  @ApiModelProperty("短信平台的发送ID")
  @Column(length = 64)
  private String sendId;

  @ApiModelProperty("完成标识")
  private boolean complete;
}
