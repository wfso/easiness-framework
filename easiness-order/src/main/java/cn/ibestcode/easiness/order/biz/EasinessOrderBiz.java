/**
 * Copyright 2020 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.order.biz;

import cn.ibestcode.easiness.core.annotation.Biz;
import cn.ibestcode.easiness.eventbus.EventBus;
import cn.ibestcode.easiness.order.domain.EasinessOrderCreateVo;
import cn.ibestcode.easiness.order.domain.EasinessOrderItemCreateVo;
import cn.ibestcode.easiness.order.domain.EasinessOrderItemUpdateVo;
import cn.ibestcode.easiness.order.domain.EasinessOrderUpdateVo;
import cn.ibestcode.easiness.order.event.OrderCreatedEvent;
import cn.ibestcode.easiness.order.event.OrderItemStatusChangeEvent;
import cn.ibestcode.easiness.order.event.OrderStatusChangeEvent;
import cn.ibestcode.easiness.order.event.OrderUpdatedEvent;
import cn.ibestcode.easiness.order.exception.EasinessOrderException;
import cn.ibestcode.easiness.order.handler.DefaultOrderPayableRuleHandler;
import cn.ibestcode.easiness.order.helper.EasinessOrderExtendHelper;
import cn.ibestcode.easiness.order.helper.EasinessOrderHelper;
import cn.ibestcode.easiness.order.model.*;
import cn.ibestcode.easiness.order.service.*;
import cn.ibestcode.easiness.utils.SpringBeanUtilsExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/06 20:13
 */
@Biz
public class EasinessOrderBiz {

  @Autowired
  private DefaultOrderPayableRuleHandler ruleHandler;
  @Autowired
  private EasinessOrderService orderService;
  @Autowired
  private EasinessOrderExtendService orderExtendService;
  @Autowired
  private EasinessOrderItemService orderItemService;
  @Autowired
  private EasinessOrderItemExtendService orderItemExtendService;
  @Autowired
  private EasinessOrderPayableRuleService orderPayableRuleService;

  @Autowired
  private EasinessOrderNotifyBiz notifyBiz;


  @Autowired
  private EventBus eventBus;

  // region 创建订单

  /**
   * 创建一个订单
   *
   * @param order         订单对象
   * @param itemCreateVos 创建订单项的VO对象
   * @param rules         订单应支付金额的计算规则对象列表
   * @param orderExtends  订单的扩展对象列表
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(EasinessOrder order, List<EasinessOrderItemCreateVo> itemCreateVos,
                       List<EasinessOrderPayableRule> rules, List<EasinessOrderExtend> orderExtends) {
    // 首先设置订单的状态为: 未付款
    order.setOrderStatus(OrderStatus.UNPAID);

    // 处理订单项
    orderItemCreateVoProcess(order, itemCreateVos);

    // 处理 订单的应支付金额
    rules = orderPayableRuleProcess(order, rules);

    // 持久化 订单
    orderService.create(order);

    // 持久化订单项
    persistOrderItemCreateVo(order, itemCreateVos);

    // 持久化 订单的应支付金额规则
    persistOrderPayableRule(order, rules);

    // 持久化 订单的扩展
    persistOrderExtend(order, orderExtends);

    // 触发订单创建完成事件
    eventBus.post(new OrderCreatedEvent(order.getUuid()));

    //返回订单的UUID
    return order.getUuid();
  }

  /**
   * 创建一个订单
   *
   * @param order         订单对象
   * @param itemCreateVos 创建订单项的VO对象
   * @param rules         订单应支付金额的计算规则对象列表
   * @param map           用于生成订单扩展对象列表的 extendKey-value 对
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(EasinessOrder order, List<EasinessOrderItemCreateVo> itemCreateVos,
                       List<EasinessOrderPayableRule> rules, Map<String, String> map) {
    return create(order, itemCreateVos, rules, EasinessOrderExtendHelper.getInstanceList(map));
  }

  /**
   * 创建一个订单
   *
   * @param order         订单对象
   * @param itemCreateVos 创建订单项的VO对象
   * @param rules         订单应支付金额的计算规则对象列表
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(EasinessOrder order, List<EasinessOrderItemCreateVo> itemCreateVos,
                       List<EasinessOrderPayableRule> rules) {
    return create(order, itemCreateVos, rules, new ArrayList<>());
  }

  /**
   * 创建一个订单
   *
   * @param order         订单对象
   * @param itemCreateVos 创建订单项的VO对象列表
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(EasinessOrder order, List<EasinessOrderItemCreateVo> itemCreateVos) {
    return create(order, itemCreateVos, new ArrayList<>());
  }

  /**
   * 创建一个订单
   *
   * @param order         订单对象
   * @param itemCreateVos 创建订单项的VO对象列表
   * @param map           用于生成订单扩展对象列表的 extendKey-value 对
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(EasinessOrder order, List<EasinessOrderItemCreateVo> itemCreateVos, Map<String, String> map) {
    return create(order, itemCreateVos, new ArrayList<>(), EasinessOrderExtendHelper.getInstanceList(map));
  }

  /**
   * 创建一个订单
   *
   * @param ownerUuid     订单所属者的唯一标识-某人或某组织
   * @param orderType     订单类型
   * @param itemCreateVos 创建订单项的VO对象列表
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(String ownerUuid, String orderType, List<EasinessOrderItemCreateVo> itemCreateVos) {
    return create(EasinessOrderHelper.getInstance(ownerUuid, orderType), itemCreateVos);
  }

  /**
   * 创建一个订单
   *
   * @param ownerUuid     订单所属者的唯一标识-某人或某组织
   * @param orderType     订单类型
   * @param itemCreateVos 创建订单项的VO对象列表
   * @param orderName     订单名称
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(String ownerUuid, String orderType, String orderName, List<EasinessOrderItemCreateVo> itemCreateVos) {
    return create(EasinessOrderHelper.getInstance(ownerUuid, orderType, orderName), itemCreateVos);
  }

  /**
   * 创建一个订单
   *
   * @param ownerUuid    订单所属者的唯一标识-某人或某组织
   * @param orderType    订单类型
   * @param itemCreateVo 创建订单项的VO对象
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(String ownerUuid, String orderType, EasinessOrderItemCreateVo itemCreateVo) {
    return create(
      EasinessOrderHelper.getInstance(ownerUuid, orderType, itemCreateVo.getOrderItem().getOrderItemName(), itemCreateVo.getOrderItem().getOrderItemInfo()),
      Arrays.asList(itemCreateVo)
    );
  }

  /**
   * 创建一个订单
   *
   * @param ownerUuid    订单所属者的唯一标识-某人或某组织
   * @param orderType    订单类型
   * @param itemCreateVo 创建订单项的VO对象
   * @param map          用于生成订单扩展对象列表的 extendKey-value 对
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(String ownerUuid, String orderType, EasinessOrderItemCreateVo itemCreateVo, Map<String, String> map) {
    return create(
      EasinessOrderHelper.getInstance(ownerUuid, orderType, itemCreateVo.getOrderItem().getOrderItemName(), itemCreateVo.getOrderItem().getOrderItemInfo()),
      Arrays.asList(itemCreateVo),
      map
    );
  }

  /**
   * 创建一个订单
   *
   * @param ownerUuid 订单所属者的唯一标识-某人或某组织
   * @param orderType 订单类型
   * @param orderItem 订单项
   * @param map       用于生成订单扩展对象列表的 extendKey-value 对
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(String ownerUuid, String orderType, EasinessOrderItem orderItem, Map<String, String> map) {
    return create(
      EasinessOrderHelper.getInstance(ownerUuid, orderType, orderItem.getOrderItemName(), orderItem.getOrderItemInfo()),
      Arrays.asList(new EasinessOrderItemCreateVo(orderItem)),
      map
    );
  }

  /**
   * 创建一个订单
   *
   * @param ownerUuid 订单所属者的唯一标识-某人或某组织
   * @param orderType 订单类型
   * @param orderItem 订单项
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(String ownerUuid, String orderType, EasinessOrderItem orderItem) {
    return create(EasinessOrderHelper.getInstance(ownerUuid, orderType), Arrays.asList(new EasinessOrderItemCreateVo(orderItem)));
  }

  /**
   * 创建一个订单
   *
   * @param vo 创建订单的VO对象
   * @return 返回订单的UUID
   */
  @Transactional
  public String create(EasinessOrderCreateVo vo) {
    return create(vo.getEasinessOrder(), vo.getOrderItemCreateVos(), vo.getPayableRules(), vo.getOrderExtends());
  }

  //endregion

  // region protected method
  protected List<EasinessOrderPayableRule> orderPayableRuleProcess(EasinessOrder order, List<EasinessOrderPayableRule> rules) {
    checkOrderStatus(order, "[orderPayableRuleProcess]", OrderStatus.UNPAID);
    order.setPayablePrice(order.getRealPrice());
    rules.sort(null);
    List<EasinessOrderPayableRule> availableRules = new ArrayList<>();
    int i = 0;
    for (EasinessOrderPayableRule rule : rules) {
      if (ruleHandler.handle(order, rule)) {
        rule.setAvailable(true);
      } else {
        rule.setAvailable(false);
      }
      rule.setWeight(i++);
      availableRules.add(rule);
    }
    return availableRules;
  }

  protected void orderItemCreateVoProcess(EasinessOrder order, List<EasinessOrderItemCreateVo> itemCreateVos) {
    checkOrderStatus(order, "[orderItemCreateVoProcess]", OrderStatus.UNPAID);
    int totalPrice = 0, realPrice = 0, quantity = 0;
    for (EasinessOrderItemCreateVo itemCreateVo : itemCreateVos) {
      EasinessOrderItem item = itemCreateVo.getOrderItem();
      if (!order.getCurrency().equalsIgnoreCase(item.getCurrency())) {
        throw new EasinessOrderException("EasinessOrder Currency and EasinessOrderItem Currency Not Equals");
      }
      item.setOrderType(order.getOrderType());
      item.setOwnerUuid(order.getOwnerUuid());
      item.setOrderStatus(order.getOrderStatus());
      totalPrice += item.getTotalPrice();
      realPrice += item.getRealPrice();
      quantity += item.getQuantity();
    }
    order.setTotalPrice(totalPrice);
    order.setRealPrice(realPrice);
    order.setQuantity(quantity);
  }

  protected void orderItemProcess(EasinessOrder order, List<EasinessOrderItem> items) {
    checkOrderStatus(order, "[orderItemProcess]", OrderStatus.UNPAID);
    int totalPrice = 0, realPrice = 0, quantity = 0;
    for (EasinessOrderItem item : items) {
      if (!order.getCurrency().equalsIgnoreCase(item.getCurrency())) {
        throw new EasinessOrderException("EasinessOrder Currency and EasinessOrderItem Currency Not Equals");
      }
      totalPrice += item.getTotalPrice();
      realPrice += item.getRealPrice();
      quantity += item.getQuantity();
    }
    order.setTotalPrice(totalPrice);
    order.setRealPrice(realPrice);
    order.setQuantity(quantity);
  }

  //endregion

  // region persist method

  /**
   * 持久化 EasinessOrderItemCreateVo（创建订单项的VO） 列表
   *
   * @param order         EasinessOrderItem（订单项）所属的 订单对象
   * @param itemCreateVos EasinessOrderItemCreateVo 对象列表
   */
  @Transactional
  public void persistOrderItemCreateVo(EasinessOrder order, List<EasinessOrderItemCreateVo> itemCreateVos) {
    checkOrderStatus(order, "[persistOrderItemCreateVo]", OrderStatus.UNPAID);
    for (EasinessOrderItemCreateVo vo : itemCreateVos) {
      persistOrderItem(order, vo.getOrderItem(), vo.getOrderItemExtends());
    }
  }

  /**
   * 持久化 EasinessOrderExtend（订单扩展对象）列表
   *
   * @param order        EasinessOrderExtend（订单扩展对象） 所属的订单对象
   * @param orderExtends EasinessOrderExtend（订单扩展对象）列表
   */
  @Transactional
  public void persistOrderExtend(EasinessOrder order, List<EasinessOrderExtend> orderExtends) {
    for (EasinessOrderExtend extend : orderExtends) {
      extend.setOrderUuid(order.getUuid());
      orderExtendService.create(extend);
    }
  }

  /**
   * 持久化 EasinessOrderPayableRule（订单应支付金额计算规则对象）列表
   *
   * @param order EasinessOrderPayableRule（订单应支付金额计算规则对象）所属的订单对象
   * @param rules EasinessOrderPayableRule（订单应支付金额计算规则对象）列表
   */
  @Transactional
  public void persistOrderPayableRule(EasinessOrder order, List<EasinessOrderPayableRule> rules) {
    checkOrderStatus(order, "[persistOrderPayableRule]", OrderStatus.UNPAID);
    for (EasinessOrderPayableRule rule : rules) {
      rule.setOrderUuid(order.getUuid());
      orderPayableRuleService.create(rule);
    }
  }

  /**
   * 持久化 EasinessOrderItem（订单项） 和 EasinessOrderItemExtend（订单项扩展） 列表
   *
   * @param order            EasinessOrderItem（订单项） 所属的订单对象
   * @param item             EasinessOrderItem（订单项）对象
   * @param orderItemExtends EasinessOrderItemExtend（订单项扩展） 列表
   */
  @Transactional
  public void persistOrderItem(EasinessOrder order, EasinessOrderItem item, List<EasinessOrderItemExtend> orderItemExtends) {
    checkOrderStatus(order, "[persistOrderItem]", OrderStatus.UNPAID);
    item.setOrderUuid(order.getUuid());
    orderItemService.create(item);
    persistOrderItemExtend(item, orderItemExtends);
  }

  /**
   * 持久化 EasinessOrderItemExtend（订单项扩展）对象
   *
   * @param item             EasinessOrderItemExtend（订单项扩展）所属的 订单项 对象
   * @param orderItemExtends EasinessOrderItemExtend（订单项扩展）列表
   */
  @Transactional
  public void persistOrderItemExtend(EasinessOrderItem item, List<EasinessOrderItemExtend> orderItemExtends) {
    for (EasinessOrderItemExtend extend : orderItemExtends) {
      extend.setOrderItemUuid(item.getUuid());
      orderItemExtendService.create(extend);
    }
  }

  //endregion

  // region replace method

  /**
   * 重置订单项扩展；如果已存在指定key的订单项扩展则更新，否则创建；
   *
   * @param itemUuid 订单项扩展 所属的 订单项对象的UUID（唯一标识）
   * @param key      订单项扩展的 extendKey
   * @param keyName  订单项扩展的 keyName
   * @param value    订单项扩展的 value
   * @return EasinessOrderItemExtend 订单项扩展对象
   */
  @Transactional
  public EasinessOrderItemExtend replaceOrderItemExtend(String itemUuid, String key, String keyName, String value) {
    EasinessOrderItemExtend extend = orderItemExtendService.getByOrderItemUuidAndExtendKey(itemUuid, key);
    if (extend != null) {
      extend.setKeyName(keyName);
      extend.setValue(value);
      orderItemExtendService.update(extend);
    } else {
      extend = new EasinessOrderItemExtend();
      extend.setOrderItemUuid(itemUuid);
      extend.setExtendKey(key);
      extend.setKeyName(keyName);
      extend.setValue(value);
      orderItemExtendService.create(extend);
    }
    return extend;
  }

  /**
   * 重置订单项扩展；如果已存在指定key（keyName）的订单项扩展则更新，否则创建；
   *
   * @param itemUuid 订单项扩展 所属的 订单项对象的UUID（唯一标识）
   * @param keyName  订单项扩展的 keyName
   * @param value    订单项扩展的 value
   * @return EasinessOrderItemExtend 订单项扩展对象
   */
  @Transactional
  public EasinessOrderItemExtend replaceOrderItemExtend(String itemUuid, String keyName, String value) {
    return replaceOrderItemExtend(itemUuid, keyName, keyName, value);
  }

  /**
   * 重置订单扩展；如果已存在指定key的订单扩展则更新，否则创建；
   *
   * @param orderUuid 订单扩展 所属的 订单对象的UUID（唯一标识）
   * @param key       订单扩展的 extendKey
   * @param keyName   订单扩展的 keyName
   * @param value     订单扩展的 value
   * @return EasinessOrderItemExtend 订单扩展对象
   */
  @Transactional
  public EasinessOrderExtend replaceOrderExtend(String orderUuid, String key, String keyName, String value) {
    EasinessOrderExtend extend = orderExtendService.getByOrderUuidAndExtendKey(orderUuid, key);
    if (extend != null) {
      extend.setKeyName(keyName);
      extend.setValue(value);
      orderExtendService.update(extend);
    } else {
      extend = new EasinessOrderExtend();
      extend.setOrderUuid(orderUuid);
      extend.setExtendKey(key);
      extend.setKeyName(keyName);
      extend.setValue(value);
      orderExtendService.create(extend);
    }
    return extend;
  }

  /**
   * 重置订单扩展；如果已存在指定key（keyName）的订单扩展则更新，否则创建；
   *
   * @param orderUuid 订单扩展 所属的 订单对象的UUID（唯一标识）
   * @param keyName   订单扩展的 keyName
   * @param value     订单扩展的 value
   * @return EasinessOrderItemExtend 订单扩展对象
   */
  @Transactional
  public EasinessOrderExtend replaceOrderExtend(String orderUuid, String keyName, String value) {
    return replaceOrderExtend(orderUuid, keyName, keyName, value);
  }

  /**
   * 重置 EasinessOrderPayableRule （订单的应支付金额计算规则），如果已存在指定的 type 的应支付金额计算规则 则更新，否则创建；
   *
   * @param orderUuid EasinessOrderPayableRule （订单的应支付金额计算规则）所属的订单对象的UUID（唯一标识）
   * @param rule      EasinessOrderPayableRule （订单的应支付金额计算规则）对象
   * @return EasinessOrderPayableRule （订单的应支付金额计算规则）对象
   */
  @Transactional
  public EasinessOrderPayableRule replaceOrderPayableRule(String orderUuid, EasinessOrderPayableRule rule) {
    checkOrderStatus(orderUuid, "[replaceOrderPayableRule]", OrderStatus.UNPAID);
    EasinessOrderPayableRule payableRule = orderPayableRuleService.getByOrderUuidAndType(orderUuid, rule.getPayableType());
    if (payableRule != null) {
      rule.setOrderUuid(null);
      SpringBeanUtilsExt.copyPropertiesIgnoreNull(rule, payableRule);
      orderPayableRuleService.update(payableRule);
    } else {
      rule.setOrderUuid(orderUuid);
      payableRule = orderPayableRuleService.create(rule);
    }
    return payableRule;
  }

  /**
   * 替换 EasinessOrderItem （订单项），如果已经存在则更新，否则创建；
   *
   * @param orderUuid EasinessOrderItem （订单项）所属的订单对象的UUID（唯一标识）
   * @param item      EasinessOrderItem （订单项）对象
   * @return EasinessOrderItem （订单项）对象
   */
  @Transactional
  public EasinessOrderItem replaceOrderItem(String orderUuid, EasinessOrderItem item) {
    checkOrderStatus(orderUuid, "[replaceOrderItem]", OrderStatus.UNPAID);
    if (StringUtils.isBlank(item.getUuid())) {
      item.setOrderUuid(orderUuid);
      return orderItemService.create(item);
    } else {
      return orderItemService.updateByUuidIgnoreNull(item);
    }
  }

  // endregion

  // region private method

  private boolean checkOrderRefund(String orderUuid, String message) {
    EasinessOrder order = orderService.readByUuid(orderUuid);
    if (order == null) {
      throw new EasinessOrderException(new StringBuilder().append(message)
        .append(" EasinessOrder not found by uuid[").append(orderUuid).append("]").toString());
    }
    return checkOrderRefund(order, message);
  }


  private boolean checkOrderRefund(EasinessOrder order, String message) {
    if (!order.isCanRefund()) {
      throw new EasinessOrderException(new StringBuilder().append(message)
        .append("The EasinessOrder can not refund by uuid[").append(order.getUuid()).append("]").toString());
    }
    return true;
  }

  private boolean checkOrderItemRefund(String itemUuid, String message) {
    EasinessOrderItem item = orderItemService.readByUuid(itemUuid);
    if (item == null) {
      throw new EasinessOrderException(new StringBuilder().append(message)
        .append(" EasinessOrder not found by uuid[").append(item).append("]").toString());
    }
    return checkOrderItemRefund(item, message);
  }


  private boolean checkOrderItemRefund(EasinessOrderItem item, String message) {
    if (!item.isCanRefund()) {
      throw new EasinessOrderException(new StringBuilder().append(message)
        .append("The EasinessOrderItem can not refund by uuid[").append(item.getUuid()).append("]").toString());
    }
    return true;
  }

  private boolean checkOrderStatus(String orderUuid, String message, OrderStatus... orderStatuses) {
    EasinessOrder order = orderService.readByUuid(orderUuid);
    if (order == null) {
      throw new EasinessOrderException(new StringBuilder().append(message)
        .append(" EasinessOrder not found by uuid[").append(orderUuid).append("]").toString());
    }
    return checkOrderStatus(order, message, orderStatuses);
  }

  private boolean checkOrderStatus(EasinessOrder order, String message, OrderStatus... orderStatuses) {
    List<OrderStatus> statusList = Arrays.asList(orderStatuses);
    if (!statusList.contains(order.getOrderStatus())) {
      StringBuilder sb = new StringBuilder().append(message)
        .append(" The OrderStatus of EasinessOrder was not [");
      for (OrderStatus status : orderStatuses) {
        sb.append(status.name()).append(",");
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append("]");
      throw new EasinessOrderException(sb.toString());
    }
    return true;
  }

  private boolean checkOrderItemStatus(String orderUuid, String message, OrderStatus... orderStatuses) {
    EasinessOrderItem item = orderItemService.readByUuid(orderUuid);
    if (item == null) {
      throw new EasinessOrderException(new StringBuilder().append(message)
        .append(" EasinessOrder not found by uuid[").append(orderUuid).append("]").toString());
    }
    return checkOrderItemStatus(item, message, orderStatuses);
  }

  private boolean checkOrderItemStatus(EasinessOrderItem item, String message, OrderStatus... orderStatuses) {
    List<OrderStatus> statusList = Arrays.asList(orderStatuses);
    if (!statusList.contains(item.getOrderStatus())) {
      StringBuilder sb = new StringBuilder().append(message)
        .append(" The OrderStatus of EasinessOrderItem was not [");
      for (OrderStatus status : orderStatuses) {
        sb.append(status.name()).append(",");
      }
      sb.deleteCharAt(sb.length() - 1);
      sb.append("]");
      throw new EasinessOrderException(sb.toString());
    }
    return true;
  }

  // endregion


  /**
   * 重新计算订单
   *
   * @param orderUuid 需要重新计算的订单的 uuid
   * @return 重新计算过的订单
   */
  @Transactional
  public EasinessOrder recalculateOrder(String orderUuid) {
    checkOrderStatus(orderUuid, "[recalculateOrder]", OrderStatus.UNPAID);
    EasinessOrder order = orderService.readByUuid(orderUuid);
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(orderUuid);
    List<EasinessOrderPayableRule> rules = orderPayableRuleService.getByOrderUuid(orderUuid);
    orderItemProcess(order, items);
    rules = orderPayableRuleProcess(order, rules);
    orderService.update(order);
    orderPayableRuleService.update(rules);
    return order;
  }

  // region 设置订单状态

  /**
   * 设置订单的状态为 DURING（支付中）
   *
   * @param orderUuid 需要设置状态的订单对象的 uuid（逻辑主键）
   * @param payUuid 支付对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderStatusDuring(String orderUuid, String payUuid) {
    checkOrderStatus(orderUuid, "[setOrderStatusDuring]", OrderStatus.UNPAID);
    EasinessOrder order = orderService.readByUuid(orderUuid);
    OrderStatus srcStatus = order.getOrderStatus();
    order.setOrderStatus(OrderStatus.DURING);
    order.setPayUuid(payUuid);
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(orderUuid);
    for (EasinessOrderItem item : items) {
      item.setOrderStatus(OrderStatus.DURING);
    }
    orderService.update(order);
    orderItemService.update(items);
    notifyBiz.during(srcStatus, order);
    eventBus.post(new OrderStatusChangeEvent(orderUuid, payUuid, OrderStatus.DURING));
  }

  /**
   * 设置订单的状态为 UNPAID（未支付）
   *
   * @param orderUuid 需要设置状态的订单对象的 uuid（逻辑主键）
   * @param payUuid 支付对象 uuid (逻辑主键)
   */
  @Transactional
  public void setOrderStatusUnpaid(String orderUuid, String payUuid) {
    checkOrderStatus(orderUuid, "[setOrderStatusUnpaid]", OrderStatus.DURING, OrderStatus.UNPAID);
    EasinessOrder order = orderService.readByUuid(orderUuid);
    if (StringUtils.isNotBlank(order.getPayUuid()) && !order.getPayUuid().equalsIgnoreCase(payUuid)) {
      throw new EasinessOrderException("PayUuidNotEqual");
    }
    order.setOrderStatus(OrderStatus.UNPAID);
    order.setPayUuid(null);
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(orderUuid);
    for (EasinessOrderItem item : items) {
      item.setOrderStatus(OrderStatus.UNPAID);
    }
    orderService.update(order);
    orderItemService.update(items);
    eventBus.post(new OrderStatusChangeEvent(orderUuid, payUuid, OrderStatus.UNPAID));
  }

  /**
   * 设置订单的状态为 PAID（已付款）
   *
   * @param orderUuid 需要设置状态的订单对象的 uuid（逻辑主键）
   * @param payUuid   对应支付对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderStatusPaid(String orderUuid, String payUuid) {
    checkOrderStatus(orderUuid, "[setOrderStatusPaid]", OrderStatus.DURING, OrderStatus.UNPAID);
    EasinessOrder order = orderService.readByUuid(orderUuid);
    order.setOrderStatus(OrderStatus.PAID);
    order.setPayUuid(payUuid);
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(orderUuid);
    for (EasinessOrderItem item : items) {
      item.setOrderStatus(OrderStatus.PAID);
    }
    orderService.update(order);
    orderItemService.update(items);
    eventBus.post(new OrderStatusChangeEvent(orderUuid, payUuid, OrderStatus.PAID));
  }

  /**
   * 设置订单的状态为 CANCEL（取消）
   *
   * @param orderUuid 需要设置状态的订单对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderStatusCancel(String orderUuid) {
    checkOrderStatus(orderUuid, "[setOrderStatusCancel]", OrderStatus.UNPAID);
    EasinessOrder order = orderService.readByUuid(orderUuid);
    order.setOrderStatus(OrderStatus.CANCEL);
    order.setComplete(true);
    order.setCompleteAt(System.currentTimeMillis());
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(orderUuid);
    for (EasinessOrderItem item : items) {
      item.setOrderStatus(OrderStatus.CANCEL);
      item.setComplete(true);
      item.setCompleteAt(System.currentTimeMillis());
    }
    orderService.update(order);
    orderItemService.update(items);
    eventBus.post(new OrderStatusChangeEvent(orderUuid, order.getPayUuid(), OrderStatus.CANCEL));
  }

  /**
   * 设置订单的状态为 REFUNDING（全部退款中）
   *
   * @param orderUuid 需要设置状态的订单对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderStatusRefunding(String orderUuid) {
    EasinessOrder order = orderService.readByUuid(orderUuid);
    checkOrderRefund(order, "[setOrderStatusRefunding]");
    checkOrderStatus(order, "[setOrderStatusRefunding]", OrderStatus.COMPLETE, OrderStatus.PART_REFUND, OrderStatus.PART_REFUNDING);
    order.setOrderStatus(OrderStatus.REFUNDING);
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(orderUuid);
    for (EasinessOrderItem item : items) {
      item.setOrderStatus(OrderStatus.REFUNDING);
    }
    orderService.update(order);
    orderItemService.update(items);
    eventBus.post(new OrderStatusChangeEvent(orderUuid, order.getPayUuid(), OrderStatus.REFUNDING));
  }

  /**
   * 设置订单的状态为 REFUND（全部退款）
   *
   * @param orderUuid 需要设置状态的订单对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderStatusRefund(String orderUuid) {
    checkOrderStatus(orderUuid, "[setOrderStatusRefund]", OrderStatus.REFUNDING);
    EasinessOrder order = orderService.readByUuid(orderUuid);
    order.setOrderStatus(OrderStatus.REFUND);
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(orderUuid);
    for (EasinessOrderItem item : items) {
      item.setOrderStatus(OrderStatus.REFUND);
    }
    orderService.update(order);
    orderItemService.update(items);
    eventBus.post(new OrderStatusChangeEvent(orderUuid, order.getPayUuid(), OrderStatus.REFUND));
  }

  /**
   * 设置订单项的状态为 PART_REFUNDING（部分退款中）
   *
   * @param orderItemUuid 需要设置状态的订单项对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderItemStatusPartRefunding(String orderItemUuid) {
    EasinessOrderItem item = orderItemService.readByUuid(orderItemUuid);
    EasinessOrder order = orderService.readByUuid(item.getOrderUuid());
    checkOrderRefund(order, "[setOrderItemStatusPartRefunding]");
    checkOrderItemRefund(item, "[setOrderItemStatusPartRefunding]");
    checkOrderStatus(order, "[setOrderItemStatusPartRefunding]", OrderStatus.COMPLETE, OrderStatus.PART_REFUND, OrderStatus.PART_REFUNDING);
    checkOrderItemStatus(item, "[setOrderItemStatusPartRefunding]", OrderStatus.COMPLETE, OrderStatus.PART_REFUND, OrderStatus.PART_REFUNDING);
    order.setOrderStatus(OrderStatus.PART_REFUNDING);
    item.setOrderStatus(OrderStatus.PART_REFUNDING);
    orderService.update(order);
    orderItemService.update(item);
    eventBus.post(new OrderItemStatusChangeEvent(orderItemUuid, OrderStatus.PART_REFUNDING));
  }

  /**
   * 设置订单项的状态为 PART_REFUND（部分退款）
   *
   * @param orderItemUuid 需要设置状态的订单项对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderItemStatusPartRefund(String orderItemUuid) {
    EasinessOrderItem item = orderItemService.readByUuid(orderItemUuid);
    EasinessOrder order = orderService.readByUuid(item.getOrderUuid());
    checkOrderStatus(order, "[setOrderItemStatusPartRefund]", OrderStatus.PART_REFUNDING);
    checkOrderItemStatus(item, "[setOrderItemStatusPartRefund]", OrderStatus.PART_REFUNDING);
    order.setOrderStatus(OrderStatus.PART_REFUND);

    // 根据现有订单项的状态，设计订单状态
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(order.getUuid());
    List<OrderStatus> statusList = Arrays.asList(OrderStatus.REFUNDING, OrderStatus.PART_REFUNDING);
    for (EasinessOrderItem orderItem : items) {
      if (statusList.contains(orderItem.getOrderStatus())) {
        order.setOrderStatus(OrderStatus.PART_REFUNDING);
        break;
      }
    }
    item.setOrderStatus(OrderStatus.PART_REFUND);
    orderService.update(order);
    orderItemService.update(item);
    eventBus.post(new OrderItemStatusChangeEvent(orderItemUuid, OrderStatus.PART_REFUND));
  }

  /**
   * 设置订单项的状态为 REFUNDING（全部退款中）
   *
   * @param orderItemUuid 需要设置状态的订单项对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderItemStatusRefunding(String orderItemUuid) {
    EasinessOrderItem item = orderItemService.readByUuid(orderItemUuid);
    EasinessOrder order = orderService.readByUuid(item.getOrderUuid());
    checkOrderRefund(order, "[setOrderItemStatusPartRefunding]");
    checkOrderItemRefund(item, "[setOrderItemStatusPartRefunding]");
    checkOrderStatus(order, "[setOrderItemStatusRefunding]", OrderStatus.COMPLETE, OrderStatus.PART_REFUND, OrderStatus.PART_REFUNDING);
    checkOrderItemStatus(item, "[setOrderItemStatusRefunding]", OrderStatus.COMPLETE, OrderStatus.PART_REFUND, OrderStatus.PART_REFUNDING);
    order.setOrderStatus(OrderStatus.REFUNDING);

    // 根据现有订单项的状态，设置订单状态
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(order.getUuid());
    List<OrderStatus> statusList = Arrays.asList(OrderStatus.COMPLETE, OrderStatus.PART_REFUND, OrderStatus.PART_REFUNDING);
    for (EasinessOrderItem orderItem : items) {
      if (statusList.contains(orderItem.getOrderStatus())) {
        order.setOrderStatus(OrderStatus.PART_REFUNDING);
        break;
      }
    }
    item.setOrderStatus(OrderStatus.REFUNDING);
    orderService.update(order);
    orderItemService.update(item);
    eventBus.post(new OrderItemStatusChangeEvent(orderItemUuid, OrderStatus.REFUNDING));
  }

  /**
   * 设置订单项的状态为 REFUND（全部退款）
   *
   * @param orderItemUuid 需要设置状态的订单项对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderItemStatusRefund(String orderItemUuid) {
    EasinessOrderItem item = orderItemService.readByUuid(orderItemUuid);
    EasinessOrder order = orderService.readByUuid(item.getOrderUuid());
    checkOrderStatus(order, "[setOrderItemStatusRefund]", OrderStatus.PART_REFUNDING, OrderStatus.REFUNDING);
    checkOrderItemStatus(item, "[setOrderItemStatusRefund]", OrderStatus.REFUNDING);
    if (order.getOrderStatus().equals(OrderStatus.REFUNDING)) {
      order.setOrderStatus(OrderStatus.REFUND);
    } else {
      order.setOrderStatus(OrderStatus.PART_REFUND);
    }

    // 根据现有订单项的状态，设置订单状态
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(order.getUuid());
    List<OrderStatus> statusList = Arrays.asList(OrderStatus.REFUNDING, OrderStatus.PART_REFUNDING);
    for (EasinessOrderItem orderItem : items) {
      if (statusList.contains(orderItem.getOrderStatus())) {
        if (order.getOrderStatus().equals(OrderStatus.REFUNDING)) {
          order.setOrderStatus(OrderStatus.REFUNDING);
        } else {
          order.setOrderStatus(OrderStatus.PART_REFUNDING);
        }
        break;
      }
    }
    item.setOrderStatus(OrderStatus.REFUND);
    orderService.update(order);
    orderItemService.update(item);
    eventBus.post(new OrderItemStatusChangeEvent(orderItemUuid, OrderStatus.REFUND));
  }

  /**
   * 设置订单的状态为 COMPLETE（完成）
   *
   * @param orderUuid 需要设置状态的订单对象的 uuid（逻辑主键）
   */
  @Transactional
  public void setOrderStatusComplete(String orderUuid) {
    checkOrderStatus(orderUuid, "[setOrderStatusComplete]", OrderStatus.PAID);
    EasinessOrder order = orderService.readByUuid(orderUuid);
    order.setOrderStatus(OrderStatus.COMPLETE);
    order.setComplete(true);
    order.setCompleteAt(System.currentTimeMillis());
    List<EasinessOrderItem> items = orderItemService.getByOrderUuid(orderUuid);
    for (EasinessOrderItem item : items) {
      item.setOrderStatus(OrderStatus.COMPLETE);
    }
    orderService.update(order);
    orderItemService.update(items);
    eventBus.post(new OrderStatusChangeEvent(orderUuid, order.getPayUuid(), OrderStatus.COMPLETE));
  }

  // endregion

  // region update method
  public EasinessOrder updateOrder(EasinessOrderUpdateVo vo) {
    EasinessOrder order = orderService.readByUuid(vo.getUuid());
    SpringBeanUtilsExt.copyPropertiesIgnoreEmpty(vo, order);
    orderService.update(order);
    eventBus.post(new OrderUpdatedEvent(order.getUuid()));
    return order;
  }

  public EasinessOrder updateOrderStatusDescription(String orderUuid, String statusDescription) {
    EasinessOrder order = orderService.readByUuid(orderUuid);
    order.setStatusDescription(statusDescription);
    orderService.update(order);
    eventBus.post(new OrderUpdatedEvent(order.getUuid()));
    return order;
  }

  public EasinessOrderItem updateOrderItem(EasinessOrderItemUpdateVo vo) {
    EasinessOrderItem item = orderItemService.readByUuid(vo.getUuid());
    SpringBeanUtilsExt.copyPropertiesIgnoreEmpty(vo, item);
    orderItemService.update(item);
    eventBus.post(new OrderUpdatedEvent(item.getOrderUuid()));
    return item;
  }

  public EasinessOrderItem updateOrderItemStatusDescription(String orderItemUuid, String statusDescription) {
    EasinessOrderItem item = orderItemService.readByUuid(orderItemUuid);
    item.setStatusDescription(statusDescription);
    orderItemService.update(item);
    eventBus.post(new OrderUpdatedEvent(item.getOrderUuid()));
    return item;
  }


  // endregion

}
