/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.form.domain;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/10/23 10:47
 */
public interface Form {
  /**
   * 表单项的类型，它并不与 HTML 表单的 type 属性对应
   * 可以自由扩展
   *
   * @return string
   */
  String getType();

  /**
   * 表单项的名称，对应 HTML 表单的 name 属性
   *
   * @return string
   */
  String getName();

  /**
   * 表单的值，用户提交表单时赶写的值
   *
   * @return string
   */
  String getValue();

  /**
   * 验证用户输入是否合法
   * 用户提供合法输入时，返回true，反之返回 false
   *
   * @return boolean
   */
  boolean checkValue();
}
