package cn.ibestcode.easiness.order.processor;

import cn.ibestcode.easiness.eventbus.EventListener;
import cn.ibestcode.easiness.eventbus.annotation.Subscribe;
import cn.ibestcode.easiness.order.biz.EasinessOrderBiz;
import cn.ibestcode.easiness.order.event.OrderItemStatusSettingEvent;
import cn.ibestcode.easiness.order.event.OrderStatusSettingEvent;
import org.springframework.beans.factory.annotation.Autowired;

@EventListener
public class OrderStatusSettingEventProcessor {
  @Autowired
  private EasinessOrderBiz orderBiz;

  @Subscribe
  public void orderStatusSettingEventProcess(OrderStatusSettingEvent event) {
    switch (event.getOrderStatus()) {
      case PAID: {
        orderBiz.setOrderStatusPaid(event.getOrderUuid(), event.getPayUuid());
        break;
      }
      case CANCEL: {
        orderBiz.setOrderStatusCancel(event.getOrderUuid());
        break;
      }
      case DURING: {
        orderBiz.setOrderStatusDuring(event.getOrderUuid(), event.getPayUuid());
        break;
      }
      case REFUND: {
        orderBiz.setOrderStatusRefund(event.getOrderUuid());
        break;
      }
      case UNPAID: {
        orderBiz.setOrderStatusUnpaid(event.getOrderUuid(), event.getPayUuid());
        break;
      }
      case COMPLETE: {
        orderBiz.setOrderStatusComplete(event.getOrderUuid());
        break;
      }
      case REFUNDING: {
        orderBiz.setOrderStatusRefunding(event.getOrderUuid());
      }
    }
  }

  @Subscribe
  public void orderItemSettingEventProcess(OrderItemStatusSettingEvent event) {
    switch (event.getOrderItemStatus()) {
      case REFUNDING: {
        orderBiz.setOrderItemStatusRefunding(event.getOrderItemUuid());
        break;
      }
      case REFUND: {
        orderBiz.setOrderItemStatusRefund(event.getOrderItemUuid());
        break;
      }
      case PART_REFUNDING: {
        orderBiz.setOrderItemStatusPartRefunding(event.getOrderItemUuid());
        break;
      }
      case PART_REFUND: {
        orderBiz.setOrderItemStatusPartRefund(event.getOrderItemUuid());
        break;
      }
    }
  }
}
