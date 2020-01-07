package cn.ibestcode.easiness.order.scheduling;

import cn.ibestcode.easiness.core.properites.EasinessApplicationProperties;
import cn.ibestcode.easiness.core.query.builder.DefaultFiltersBuilder;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import cn.ibestcode.easiness.order.model.EasinessOrder;
import cn.ibestcode.easiness.order.model.EasinessOrderItem;
import cn.ibestcode.easiness.order.model.OrderStatus;
import cn.ibestcode.easiness.order.service.EasinessOrderItemService;
import cn.ibestcode.easiness.order.service.EasinessOrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
@Slf4j
public class EasinessOrderScheduling {

  @Autowired
  private EasinessOrderService  orderService;

  @Autowired
  private EasinessOrderItemService  itemService;

  @Autowired
  private RedissonClient redissonClient;

  @Autowired
  private EasinessApplicationProperties applicationProperties;

  /**
   * 设置订单和订单项的 完成标识 和 完成时间
   */
  @Scheduled(cron = "0 0 * * * ?")
  public void completeOrder() {

    RLock lock = redissonClient.getLock(applicationProperties.getId() + "easiness-order-EasinessOrderScheduling-completeOrder");

    try {
      if (lock.tryLock(0, 3, TimeUnit.HOURS)) {

        IFilter filter = DefaultFiltersBuilder.getAndInstance()
          .andIsFalse("complete")
          .andLessThen("refundTimeout", String.valueOf(System.currentTimeMillis()))
          .andIn(
            "orderStatus",
            Arrays.asList(
              OrderStatus.CANCEL.name(),
              OrderStatus.REFUND.name(),
              OrderStatus.PART_REFUND.name(),
              OrderStatus.COMPLETE.name()
            ),
            OrderStatus.class
          )
          .build();

        // 设置订单
        List<EasinessOrder> orders = orderService.getList(filter);
        for (EasinessOrder order : orders) {
          order.setCompleteAt(System.currentTimeMillis());
          order.setComplete(true);
        }
        orderService.update(orders);

        // 设置订单项
        List<EasinessOrderItem> items = itemService.getList(filter);
        for (EasinessOrderItem item : items) {
          item.setCompleteAt(System.currentTimeMillis());
          item.setComplete(true);
        }
        itemService.update(items);

        lock.unlock();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      log.warn(e.getMessage(), e);
    }

  }

}
