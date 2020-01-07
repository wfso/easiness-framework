/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.helper;

import cn.ibestcode.easiness.order.model.EasinessOrderPayableRule;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
public class EasinessOrderPayableRuleHelper {

  /**
   * 用于生成 EasinessOrderPayableRule 对象的 工具函数
   *
   * @param type      类型-对应一个 IOrderPayableRuleHandler 类来处理这个类型的 EasinessOrderPayableRule
   * @param weight    权重-值越小，越早被处理，越早生效
   * @param data      数据- IOrderPayableRuleHandler 做处理时需要用到的数据，一般为JSON格式
   * @param name      名称-显示给用户看的
   * @param operation 操作说明-显示给用户看的
   * @param rule      生效规则说明-显示给用户看的
   * @return EasinessOrderPayableRule 对象
   */
  public static EasinessOrderPayableRule getInstance(String type, int weight, String data, String name, String operation, String rule) {
    EasinessOrderPayableRule payableRule = new EasinessOrderPayableRule();
    payableRule.setAvailable(true);
    payableRule.setPayableType(type);
    payableRule.setWeight(weight);
    payableRule.setPayableData(data);
    payableRule.setName(name);
    payableRule.setOperation(operation);
    payableRule.setRule(rule);
    return payableRule;
  }

  /**
   * 用于生成 EasinessOrderPayableRule 对象的 工具函数
   *
   * @param type      类型-对应一个 IOrderPayableRuleHandler 类来处理这个类型的 EasinessOrderPayableRule
   * @param weight    权重-值越小，越早被处理，越早生效
   * @param data      数据- IOrderPayableRuleHandler 做处理时需要用到的数据，一般为JSON格式
   * @param name      名称-显示给用户看的
   * @param operation 操作说明-显示给用户看的
   * @return EasinessOrderPayableRule 对象
   */
  public static EasinessOrderPayableRule getInstance(String type, int weight, String data, String name, String operation) {
    return getInstance(type, weight, data, name, operation, null);
  }

  /**
   * 用于生成 EasinessOrderPayableRule 对象的 工具函数
   *
   * @param type   类型-对应一个 IOrderPayableRuleHandler 类来处理这个类型的 EasinessOrderPayableRule
   * @param weight 权重-值越小，越早被处理，越早生效
   * @param data   数据- IOrderPayableRuleHandler 做处理时需要用到的数据，一般为JSON格式
   * @param name   名称-显示给用户看的
   * @return EasinessOrderPayableRule 对象
   */
  public static EasinessOrderPayableRule getInstance(String type, int weight, String data, String name) {
    return getInstance(type, weight, data, name, null);
  }

  /**
   * 用于生成 EasinessOrderPayableRule 对象的 工具函数
   *
   * @param type   类型-对应一个 IOrderPayableRuleHandler 类来处理这个类型的 EasinessOrderPayableRule
   * @param weight 权重-值越小，越早被处理，越早生效
   * @param data   数据- IOrderPayableRuleHandler 做处理时需要用到的数据，一般为JSON格式
   * @return EasinessOrderPayableRule 对象
   */
  public static EasinessOrderPayableRule getInstance(String type, int weight, String data) {
    return getInstance(type, weight, data, type);
  }

  /**
   * 用于生成 EasinessOrderPayableRule 对象的 工具函数
   *
   * @param type   类型-对应一个 IOrderPayableRuleHandler 类来处理这个类型的 EasinessOrderPayableRule
   * @param weight 权重-值越小，越早被处理，越早生效
   * @return EasinessOrderPayableRule 对象
   */
  public static EasinessOrderPayableRule getInstance(String type, int weight) {
    return getInstance(type, weight, null);
  }

  /**
   * 用于生成 EasinessOrderPayableRule 对象的 工具函数
   *
   * @param type 类型-对应一个 IOrderPayableRuleHandler 类来处理这个类型的 EasinessOrderPayableRule
   * @return EasinessOrderPayableRule 对象
   */
  public static EasinessOrderPayableRule getInstance(String type) {
    return getInstance(type, 0);
  }
}
