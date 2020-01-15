package cn.ibestcode.easiness.pay.wechat.domain;

import cn.ibestcode.easiness.pay.domain.PlaceOrderResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/14 18:53
 */
@Getter
@Setter
public class WechatPCWebPlaceOrderResult implements PlaceOrderResult, Serializable {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private String return_code;
  private String return_msg;

  private String appid;
  private String mch_id;
  private String device_info;
  private String nonce_str;
  private String sign;
  private String result_code;
  private String err_code;
  private String err_code_des;

  private String trade_type;
  private String prepay_id;
  private String code_url;

  @Override
  public String getTradeId() {
    return prepay_id;
  }

  @Override
  public String getOutTradeId() {
    return null;
  }

  @Override
  public boolean isSucceed() {
    return "SUCCESS".equalsIgnoreCase(return_code)
      && "SUCCESS".equalsIgnoreCase(result_code);
  }

  @Override
  public String toJSON() {
    try {
      return objectMapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }

  @Override
  public String getResponseBody() {
    return code_url;
  }
}
