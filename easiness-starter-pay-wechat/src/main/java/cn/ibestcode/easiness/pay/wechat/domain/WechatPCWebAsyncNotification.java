package cn.ibestcode.easiness.pay.wechat.domain;

import cn.ibestcode.easiness.pay.domain.EasinessPayPassbackParams;
import cn.ibestcode.easiness.pay.domain.PayAsyncNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class WechatPCWebAsyncNotification implements PayAsyncNotification, Serializable {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private String return_code;
  private String return_msg;
  private String appid;
  private String mch_id;
  private String device_info;
  private String nonce_str;
  private String sign;
  private String sign_type;
  private String result_code;
  private String err_code;
  private String err_code_des;
  private String openid;
  private String is_subscribe;
  private String trade_type;
  private String bank_type;
  private String total_fee;
  private String settlement_total_fee;
  private String fee_type;
  private String cash_fee;
  private String cash_fee_type;
  private String coupon_fee;
  private String coupon_count;
  private String transaction_id;
  private String out_trade_no;
  private String attach;
  private String time_end;
  private String platformId;
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
