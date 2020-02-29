package cn.ibestcode.easiness.pay.wechat.domain;

import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.domain.PayAsyncNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/14 18:52
 */
@Setter
@Getter
@ApiModel("微信支付异步通知对象-基类")
public class WechatAsyncNotification implements PayAsyncNotification, Serializable {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @ApiModelProperty("返回状态码")
  private String return_code;
  @ApiModelProperty("返回信息")
  private String return_msg;
  @ApiModelProperty("APPID")
  private String appid;
  @ApiModelProperty("商户号")
  private String mch_id;
  @ApiModelProperty("设备号")
  private String device_info;
  @ApiModelProperty("随机字符串")
  private String nonce_str;
  @ApiModelProperty("签名")
  private String sign;
  @ApiModelProperty("签名类型")
  private String sign_type;
  @ApiModelProperty("业务结果")
  private String result_code;
  @ApiModelProperty("错误代码")
  private String err_code;
  @ApiModelProperty("错误代码描述")
  private String err_code_des;
  @ApiModelProperty("用户标识")
  private String openid;
  @ApiModelProperty("是否关注公众账号")
  private String is_subscribe;
  @ApiModelProperty("交易类型")
  private String trade_type;
  @ApiModelProperty("付款银行")
  private String bank_type;
  @ApiModelProperty("订单金额")
  private String total_fee;
  @ApiModelProperty("应结订单金额")
  private String settlement_total_fee;
  @ApiModelProperty("货币种类")
  private String fee_type;
  @ApiModelProperty("现金支付金额")
  private String cash_fee;
  @ApiModelProperty("现金支付货币类型")
  private String cash_fee_type;
  @ApiModelProperty("总代金券金额")
  private String coupon_fee;
  @ApiModelProperty("代金券使用数量")
  private String coupon_count;
  @ApiModelProperty("微信支付订单号")
  private String transaction_id;
  @ApiModelProperty("商户订单号")
  private String out_trade_no;
  @ApiModelProperty("商家数据包")
  private String attach;
  @ApiModelProperty("支付完成时间")
  private String time_end;
  @ApiModelProperty("平台ID-开发者配置")
  private String platformId;
  @ApiModelProperty("服务器ID-开发者配置")
  private String workerId;

  @Override
  public String getTradeId() {
    return transaction_id;
  }

  @Override
  public String getOutTradeId() {
    return out_trade_no;
  }

  @Override
  public String getSellerId() {
    return mch_id;
  }

  @Override
  public String getBuyerId() {
    return openid;
  }

  @Override
  public String getTotalAmount() {
    return total_fee;
  }

  @Override
  public String getAppId() {
    return appid;
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
    return "SUCCESS".equalsIgnoreCase(return_code)
      && "SUCCESS".equalsIgnoreCase(result_code);
  }

  @Override
  public boolean isClosed() {
    return !isSucceed();
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
    if (StringUtils.isBlank(attach)) {
      return;
    }
    try {
      EasinessPayPassbackParams params = objectMapper.readValue(attach, EasinessPayPassbackParams.class);
      this.platformId = params.getPlatformId();
      this.workerId = params.getWorkerId();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
