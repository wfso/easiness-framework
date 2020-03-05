package cn.ibestcode.easiness.pay.wechat.domain;

import cn.ibestcode.easiness.pay.domain.PlaceOrderResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wfso (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/14 18:53
 */
@Getter
@Setter
@ApiModel("微信下单接口通用返回值")
public class WechatPlaceOrderResult implements PlaceOrderResult, Serializable {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @ApiModelProperty("返回状态码")
  private String return_code;
  @ApiModelProperty("返回信息")
  private String return_msg;

  @ApiModelProperty("应用APPID")
  private String appid;
  @ApiModelProperty("商户号")
  private String mch_id;
  @ApiModelProperty("设备号")
  private String device_info;
  @ApiModelProperty("随机字符串")
  private String nonce_str;
  @ApiModelProperty("签名")
  private String sign;
  @ApiModelProperty("业务结果")
  private String result_code;
  @ApiModelProperty("错误代码")
  private String err_code;
  @ApiModelProperty("错误代码描述")
  private String err_code_des;

  @ApiModelProperty("trade_type")
  private String trade_type;
  @ApiModelProperty("prepay_id")
  private String prepay_id;
  @ApiModelProperty("二维码链接")
  private String code_url;
  @ApiModelProperty("h5支付的跳转链接")
  private String mweb_url;

  @ApiModelProperty("下单接口的返回内容")
  private String responseBody;

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
}
