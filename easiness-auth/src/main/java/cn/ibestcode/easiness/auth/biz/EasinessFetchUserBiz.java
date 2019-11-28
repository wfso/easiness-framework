/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.auth.biz;


import cn.ibestcode.easiness.auth.model.EasinessUser;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
public interface EasinessFetchUserBiz {
  EasinessUser getByIdentification(String identification);
}
