package cn.ibestcode.easiness.pay;

public class EasinessPayConstant {
  // 默认的异步通知前缀
  public static final String ASYNC_NOTIFY_URL_PREFIX = "/api/easiness/pay/notify";

  // 支付的默认过期时间
  // 需要两个小时以内，否则微信支付会有bug，因为微信的 prepay_id 的有效期最长为两个小时
  public static final String PAY_DEFAULT_EXPIRE = "easiness.pay.expire";
}
