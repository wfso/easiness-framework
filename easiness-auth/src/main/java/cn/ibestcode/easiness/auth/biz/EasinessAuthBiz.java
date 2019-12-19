/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.auth.biz;

import cn.ibestcode.easiness.auth.domain.EasinessLoginBeforeResult;
import cn.ibestcode.easiness.auth.domain.EasinessLoginResult;
import cn.ibestcode.easiness.auth.domain.RoleAndPermissionResult;
import cn.ibestcode.easiness.auth.model.EasinessPermission;
import cn.ibestcode.easiness.auth.model.EasinessRole;
import cn.ibestcode.easiness.auth.model.EasinessUser;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/26 19:20
 */
public interface EasinessAuthBiz {

  /**
   * 登录前获取用户信息，只返回基本的盐、当次登录token等信息
   * 如果用户已经在其他地方登录，则返回当前登录的ip地址
   *
   * @param username 抽象用户名
   * @return EasinessLoginBeforeResult
   */
  EasinessLoginBeforeResult loginBefore(String username);

  /**
   * 登录接口
   *
   * @param type 本次登录的类型
   * @return EasinessLoginResult
   */
  EasinessLoginResult login(String type);

  /**
   * 查检是否登录的接口
   * 如果未登录则抛出异常
   * 如果已经登录，则返回基本的信息信息（与登录接口相同）
   *
   * @return EasinessLoginResult
   */
  EasinessLoginResult checkLogin();

  /**
   * 检查用户角色
   *
   * @param code 如果返回值中角色列表中有多个角色
   *             且主角色为null，则需要再次调用并选中一个角色
   *             参数为选中角色的code
   * @return RoleAndPermissionResult
   */
  RoleAndPermissionResult checkRole(String code);

  /**
   * 获取当前登录用户的ID
   *
   * @return 前登录用户的ID
   */
  long getLoginUserId();

  /**
   * 获取当前登录用户的主角色的ID
   *
   * @return 登录用户主角色的ID
   */
  long getMasterRoleId();

  /**
   * 获取当前登录用户的主角色
   *
   * @return 登录用户主角色
   */
  EasinessRole getMasterRole();

  /**
   * 返回当前登录用户所拥有角色的code 列表
   *
   * @return 字符串列表
   */
  List<String> getLoginRoleCodes();

  /**
   * 返回当前登录用户所拥有角色的 列表
   *
   * @return 字符串列表
   */
  List<EasinessRole> getLoginRoles();


  /**
   * 返回当前登录用户所有权限的code 列表
   *
   * @return 字符串列表
   */
  List<String> getAvailablePermissionCodes();

  /**
   * 返回当前登录用户所有权限的 列表
   *
   * @return 字符串列表
   */
  List<EasinessPermission> getAvailablePermissions();

  /**
   * 返回当前登录用户主权限的code 列表
   * 用户的主权限，与用户的当前主角色有关
   * 是用户权限 与 用户当前主角色权限的集合
   *
   * @return 字符串列表
   */
  List<String> getCurrentPermissionCodes();

  /**
   * 返回当前登录用户主权限的 列表
   * 用户的主权限，与用户的当前主角色有关
   * 是用户权限 与 用户当前主角色权限的集合
   *
   * @return 字符串列表
   */
  List<EasinessPermission> getCurrentPermissions();

  /**
   * 通过用户标识获取用户对象
   * 用户标识可以是任何可以唯一标识一个用户的属性
   * 如：手机、身份证号，登录名等
   *
   * @param identification 用户标识
   * @return 用户对象
   */
  EasinessUser getUserByIdentification(String identification);

  /**
   * 验证密码
   *
   * @param dbPassword      数据库中的密码
   * @param requestPassword 用户请求中提交的密码
   * @return 密码验证结果 通过返回 true 否则返回 false
   */
  boolean validateCredentials(String dbPassword, String requestPassword);

  /**
   * 退出登录
   */
  void logout();


  /**
   * 设置 Session
   *
   * @param key   session的键
   * @param value session的值
   */
  void setSession(String key, Object value);

  /**
   * 通过session的键 获取session的值
   *
   * @param key session的键
   * @return 对应key的session值
   */
  Object getSession(String key);

  /**
   * 通过session的键 删除session的值
   *
   * @param key session的键
   * @return 对应key的session值
   */
  Object removeSession(String key);

  /**
   * 获取sessionId
   *
   * @return sessionId
   */
  String getSessionId();


}
