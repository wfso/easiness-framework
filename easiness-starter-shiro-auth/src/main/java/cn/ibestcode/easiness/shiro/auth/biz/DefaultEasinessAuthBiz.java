/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.shiro.auth.biz;

import cn.ibestcode.easiness.auth.biz.AbstractEasinessAuthBiz;
import cn.ibestcode.easiness.shiro.session.utils.EasinessSessionUtil;
import cn.ibestcode.easiness.shiro.token.EasinessAuthenticationToken;
import cn.ibestcode.easiness.utils.RandomUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/28 18:33
 */
@Component
public class DefaultEasinessAuthBiz extends AbstractEasinessAuthBiz {

  @Override
  protected void setSession(String key, String value) {
    EasinessSessionUtil.setSession(key, value);
  }

  @Override
  protected String getSession(String key) {
    return (String) EasinessSessionUtil.getSession(key);
  }

  @Override
  protected String getSessionId() {
    return EasinessSessionUtil.getSessionId();
  }

  @Override
  protected void recordLoginStatus(long userId) {
    SecurityUtils.getSubject().login(new EasinessAuthenticationToken(userId, RandomUtil.generateUnseparatedUuid()));
  }

  @Override
  public void logout() {
    SecurityUtils.getSubject().logout();
  }
}
