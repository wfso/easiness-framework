/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.shiro.auth.handler;

import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import cn.ibestcode.easiness.auth.service.EasinessPermissionService;
import cn.ibestcode.easiness.auth.service.EasinessRoleService;
import cn.ibestcode.easiness.exception.handler.AbstractExceptionHandler;
import cn.ibestcode.easiness.exception.properties.EasinessExceptionTipsProperties;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DefaultAuthorizationExceptionHandler extends AbstractExceptionHandler {

  @Autowired
  private EasinessExceptionTipsProperties tipsProperties;

  protected EasinessPermissionService permissionService;

  protected EasinessRoleService roleService;

  @ResponseBody
  @ExceptionHandler(AuthorizationException.class)
  ResponseEntity<Object> defaultAuthorizationExceptionHandle(Throwable throwable) {
    Map<String, Object> result = new HashMap<>();
    String code = throwable.getMessage();
    String msg = null;

    // 无登录
    if (code.startsWith("This subject is anonymous")
      || code.startsWith("The current Subject is not authenticated")) {
      code = "not-logged-in";
      String msgKey = throwable.getClass().getSimpleName() + "." + code;
      if (messageSource != null) {
        msg = messageSource.getMessage(msgKey, null, code, LocaleContextHolder.getLocale());
      }
    }

    // 无权限
    else if (code.startsWith("Subject does not have permission")) {
      code = code.substring(34, code.length() - 1);
      if (messageSource != null) {
        String msgParam = "";
        if (permissionService != null) {
          EasinessPermission permission = permissionService.getByCode(code);
          msgParam = (permission == null ? code : permission.getName());
        }
        String msgKey = throwable.getClass().getSimpleName() + ".subject-does-not-have-permission";
        code = "subject-does-not-have-permission[" + code + "]";
        msg = messageSource.getMessage(msgKey, new String[]{msgParam}, code, LocaleContextHolder.getLocale());
      }
    }

    // 无角色
    else if (code.startsWith("Subject does not have role")) {
      code = code.substring(28, code.length() - 1);
      if (messageSource != null) {
        String msgParam = "";
        if (roleService != null) {
          EasinessRole role = roleService.getByCode(code);
          msgParam = (role == null ? code : role.getName());
        }
        String msgKey = throwable.getClass().getSimpleName() + ".subject-does-not-have-role";
        code = "subject-does-not-have-role[" + code + "]";
        msg = messageSource.getMessage(msgKey, new String[]{msgParam}, code, LocaleContextHolder.getLocale());
      }
    }

    // guest-only
    else if (code.startsWith("Attempting to perform a guest-only operation")) {
      code = "guest-only";
      String msgKey = throwable.getClass().getSimpleName() + "." + code;
      if (messageSource != null) {
        msg = messageSource.getMessage(msgKey, null, code, LocaleContextHolder.getLocale());
      }
    }

    // 自定义访问权限异常 SystemPermissionException
    else if (code.startsWith("SPE-")) {
      String msgKey = throwable.getClass().getSimpleName() + "." + code;
      if (messageSource != null) {
        msg = messageSource.getMessage(msgKey, null, code, LocaleContextHolder.getLocale());
      }
    }

    // 当 msg 没有被赋值时
    if (msg == null) {
      msg = code;
    }

    result.put(tipsProperties.getCodeName(), code);
    result.put(tipsProperties.getMsgName(), msg);
    return new ResponseEntity(result, HttpStatus.OK);
  }
}
