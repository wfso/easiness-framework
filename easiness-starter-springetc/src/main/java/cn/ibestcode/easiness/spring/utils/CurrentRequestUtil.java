package cn.ibestcode.easiness.spring.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CurrentRequestUtil {
  public static String getHost() {
    HttpServletRequest request = ServletUtil.getHttpServletRequest();
    return request.getHeader("host");
  }

  public static String getBaseURL() {
    String url = getURL();
    String uri = getURI();
    return url.substring(0, url.length() - uri.length() + 1);
  }

  public static String getPort() {
    return String.valueOf(ServletUtil.getHttpServletRequest().getServerPort());
  }

  public static String getMethod() {
    return ServletUtil.getHttpServletRequest().getMethod();
  }

  public static String getPath() {
    return ServletUtil.getHttpServletRequest().getServletPath();
  }

  public static String getURL() {
    return ServletUtil.getHttpServletRequest().getRequestURL().toString();
  }

  public static String getURI() {
    return ServletUtil.getHttpServletRequest().getRequestURI();
  }

  public static String getProtocol() {
    return ServletUtil.getHttpServletRequest().getProtocol();
  }

  public static String getQueryString() {
    return ServletUtil.getHttpServletRequest().getQueryString();
  }

  public static Map<String, String[]> getParameterMap() {
    return ServletUtil.getHttpServletRequest().getParameterMap();
  }

  public static Map<String, String> getParameters() {
    Map<String, String> params = new HashMap<>();
    HttpServletRequest request = ServletUtil.getHttpServletRequest();
    Enumeration<String> enumeration = request.getParameterNames();
    while (enumeration.hasMoreElements()) {
      String paramName = enumeration.nextElement();
      params.put(paramName, request.getParameter(paramName));
    }
    return params;
  }

  public static String getParameter(String paramName) {
    HttpServletRequest request = ServletUtil.getHttpServletRequest();
    return request.getParameter(paramName);
  }


  public static String getReferer() {
    return getHeader("referer");
  }


  public static String getHeader(String headerName) {
    HttpServletRequest request = ServletUtil.getHttpServletRequest();
    return request.getHeader(headerName);
  }

  public static String getBody() {
    HttpServletRequest request = ServletUtil.getHttpServletRequest();
    StringWriter writer = new StringWriter();
    char[] buf = new char[1024];
    try {
      BufferedReader reader = request.getReader();
      while (reader.read(buf) != -1) {
        writer.write(buf);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return writer.getBuffer().toString();
  }
}
