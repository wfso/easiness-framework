/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.auth.domain;

import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/26 20:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAndPermissionResult implements Serializable {
  EasinessRole masterRole;
  List<EasinessRole> roles = new ArrayList<>();
  List<EasinessPermission> permissions = new ArrayList<>();
}
