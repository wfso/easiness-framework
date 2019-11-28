package cn.ibestcode.easiness.auth.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CrossDomainFilter implements Filter {
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
    response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
    response.addHeader("Access-Control-Max-Age", "18000");
    response.addHeader("Access-Control-Allow-Credentials","true");
    if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
      response.setStatus(HttpStatus.OK.value());
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }
}
