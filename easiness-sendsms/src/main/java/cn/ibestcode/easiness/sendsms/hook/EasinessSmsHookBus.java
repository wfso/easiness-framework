/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.sendsms.hook;

import cn.ibestcode.easiness.sendsms.exception.SendSmsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/12/15 20:30
 */
@Slf4j
public class EasinessSmsHookBus {

  @Autowired
  protected List<EasinessSmsHook> smsHookList;

  protected Map<String, EasinessSmsHook> smsHookMap;

  public String hook(String type) {
    SmsHookResult result = doHook(type);
    return result.getResponse();
  }

  protected SmsHookResult doHook(String type) {
    EasinessSmsHook hook = getHookByType(type);
    if (hook == null) {
      throw new SendSmsException("HookCanNotBeNull");
    }
    return hook.hook();
  }

  protected EasinessSmsHook getHookByType(String type) {
    if (smsHookMap == null) {
      smsHookMap = new HashMap<>();
      for (EasinessSmsHook hook : smsHookList) {
        smsHookMap.put(hook.supportType(), hook);
      }
    }
    if (!smsHookMap.containsKey(type)) {
      log.warn("No available EasinessSmsHook was found by type [{}]", type);
      throw new SendSmsException("NotHookExistOfType", type);
    }
    return smsHookMap.get(type);
  }
}
