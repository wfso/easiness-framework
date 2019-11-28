/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.session.manager;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
public class EasinessSessionManager extends DefaultWebSessionManager {

  public String getTokenIdentification() {
    return tokenIdentification;
  }

  public void setTokenIdentification(String tokenIdentification) {
    this.tokenIdentification = tokenIdentification;
    Cookie cookie = new SimpleCookie(this.tokenIdentification);
    cookie.setHttpOnly(true);
    this.setSessionIdCookie(cookie);
  }

  private String tokenIdentification = "IBESTCODE-COMMON-SHIRO-TOKEN";

  public EasinessSessionManager(String tokenIdentification) {
    super();
    this.tokenIdentification = tokenIdentification;
    Cookie cookie = new SimpleCookie(this.tokenIdentification);
    cookie.setHttpOnly(true);
    this.setSessionIdCookie(cookie);
  }

  public EasinessSessionManager() {
    super();
  }


  @Override
  protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
    String id = WebUtils.toHttp(request).getHeader(tokenIdentification);

    if (!StringUtils.isEmpty(id)) {
      request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "Stateless request");
      request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
      request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
      return id;
    } else {
      //否则按默认规则从cookie取sessionId
      return super.getSessionId(request, response);
    }
  }

}
