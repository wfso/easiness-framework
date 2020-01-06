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


/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/28 18:33
 */

public class EasinessShiroAuthBiz extends AbstractEasinessAuthBiz {

  @Override
  public void setSession(String key, Object value) {
    EasinessSessionUtil.setSession(key, value);
  }

  @Override
  public Object getSession(String key) {
    return EasinessSessionUtil.getSession(key);
  }

  @Override
  public Object removeSession(String key) {
    return EasinessSessionUtil.removeSession(key);
  }

  @Override
  public String getSessionId() {
    return EasinessSessionUtil.getSessionId();
  }

  @Override
  protected void recordLoginStatus(long userId) {
    if (userId > 0) {
      SecurityUtils.getSubject().login(new EasinessAuthenticationToken(userId, RandomUtil.generateUnseparatedUuid()));
    }
  }

  @Override
  public long getLoginUserId() {
    return (long) SecurityUtils.getSubject().getPrincipal();
  }


  @Override
  public void logout() {
    SecurityUtils.getSubject().logout();
  }
}