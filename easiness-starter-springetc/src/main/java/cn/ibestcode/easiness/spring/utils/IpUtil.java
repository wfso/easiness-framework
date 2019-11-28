package cn.ibestcode.easiness.spring.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {
  /**
   * 获取客户端IP地址
   *
   * @return 返回客户端的IP地址
   */
  public static String getClientIpAddress() {
    HttpServletRequest request = ServletUtil.getHttpServletRequest();
    String ipAddress = request.getHeader("x-forwarded-for");
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getRemoteAddr();
      if (ipAddress.equals("127.0.0.1")) {
        // 根据网卡取本机配置的IP
        ipAddress = getServerIpAddress();
      }
    }
    // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
    if (ipAddress != null && ipAddress.contains(",")) {
      ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
    }
    return ipAddress;
  }

  /**
   * 根据网卡取本机配置的IP
   *
   * @return 本机IP地址
   */
  public static String getServerIpAddress() {
    try {
      InetAddress inet = InetAddress.getLocalHost();
      return inet.getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return "";
  }
}
