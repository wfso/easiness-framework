package cn.ibestcode.easiness.auth.password.handler;

import cn.ibestcode.easiness.auth.biz.EasinessAuthBiz;
import cn.ibestcode.easiness.auth.biz.EasinessUserRelationBiz;
import cn.ibestcode.easiness.auth.handler.EasinessLoginHandler;
import cn.ibestcode.easiness.auth.model.EasinessUser;
import cn.ibestcode.easiness.auth.password.EasinessPasswordAuthConstant;
import cn.ibestcode.easiness.auth.password.exception.PasswordAuthenticationException;
import cn.ibestcode.easiness.auth.service.EasinessUserRelationService;
import cn.ibestcode.easiness.spring.utils.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class EasinessPasswordLoginHandler implements EasinessLoginHandler {

  @Autowired
  private EasinessAuthBiz authBiz;

  @Autowired
  protected EasinessUserRelationBiz userRelationBiz;

  @Autowired
  protected EasinessUserRelationService userRelationService;


  @Override
  public String supportType() {
    return EasinessPasswordAuthConstant.LOGIN_TYPE;
  }

  @Override
  public long loginHandle() {
    HttpServletRequest request = ServletUtil.getHttpServletRequest();
    String username = request.getParameter("username");
    if (StringUtils.isEmpty(username) || StringUtils.isBlank(username)) {
      throw new PasswordAuthenticationException("UsernameCanNotBeEmpty");
    }
    String password = request.getParameter("password");
    if (StringUtils.isEmpty(password) || StringUtils.isBlank(password)) {
      throw new PasswordAuthenticationException("PasswordCanNotBeEmpty");
    }
    EasinessUser user = authBiz.getUserByIdentification(username);

    if (user == null || !authBiz.validateCredentials(user.getPassword(), password)) {
      throw new PasswordAuthenticationException("UsernameOrPasswordError");
    }

    if (!user.isAvailable()) {
      throw new PasswordAuthenticationException("UserDisabled");
    }

    return user.getUserId();
  }

}
