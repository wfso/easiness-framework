package cn.ibestcode.easiness.core.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DefaultPageableGenerator implements PageableGenerator {

  private int page;
  private int size;

  private List<Order> orders = new ArrayList<>();

  @Override
  public Pageable generatePageable() {
    PageableBuilder builder = PageableBuilder.from(page, size);
    for (Order order : orders) {
      builder.addOrderBy(order.getDirection(), order.getProperty());
    }
    return builder.build();
  }

  public DefaultPageableGenerator addOrderBy(Sort.Direction direction, String... properties) {
    for (String property : properties) {
      addOrder(new Order(direction, property));
    }
    return this;
  }

  public DefaultPageableGenerator addOrder(Order order) {
    orders.add(order);
    return this;
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Order {
    private Sort.Direction direction;
    private String property;
  }
}
