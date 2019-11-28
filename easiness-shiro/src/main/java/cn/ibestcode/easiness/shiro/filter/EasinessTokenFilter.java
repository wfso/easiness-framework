/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.filter;

import cn.ibestcode.easiness.shiro.session.utils.EasinessSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Slf4j
public class EasinessTokenFilter implements Filter {

  private String tokenIdentification;

  public EasinessTokenFilter(String tokenIdentification) {
    this.tokenIdentification = tokenIdentification;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    log.debug("exec EasinessTokenFilter");
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String token = request.getHeader(tokenIdentification);
    String sessionId = EasinessSessionUtil.getSessionId();
    if (StringUtils.isBlank(token) || !token.equalsIgnoreCase(sessionId)) {
      log.debug("EasinessTokenFilter - set response header "
        + tokenIdentification + ":" + sessionId);
      response.setHeader(tokenIdentification, sessionId);
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
