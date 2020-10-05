package cn.ibestcode.easiness.spring.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletUtil {
  public static ServletRequestAttributes getServletRequestAttributes() {
    return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
  }

  public static HttpServletRequest getHttpServletRequest() {
    return getServletRequestAttributes().getRequest();
  }

  public static HttpServletResponse getHttpServletResponse() {
    return getServletRequestAttributes().getResponse();
  }

  public static ServletContext getServletContext(){
    return getHttpServletRequest().getServletContext();
  }

  public static HttpSession getHttpSession(){
    return getHttpServletRequest().getSession();
  }

}
