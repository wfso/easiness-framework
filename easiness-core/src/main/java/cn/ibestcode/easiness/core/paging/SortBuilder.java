package cn.ibestcode.easiness.core.paging;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortBuilder {

  private List<Sort.Order> orders;

  private static Sort.Direction defaultDirection = Sort.Direction.ASC;

  private SortBuilder() {
    this.orders = new ArrayList<>();
  }

  private SortBuilder(Sort.Order... orders) {
    for (Sort.Order order : orders) {
      this.orders.add(order);
    }
  }

  private SortBuilder(Sort.Direction direction, String... properties) {
    for (String property : properties) {
      this.orders.add(new Sort.Order(direction, property));
    }
  }

  private SortBuilder(Sort sort) {
    for (Sort.Order order : sort) {
      this.orders.add(order);
    }
  }


  public static SortBuilder from() {
    return new SortBuilder();
  }


  public static SortBuilder from(Sort.Direction direction, String properties) {
    return new SortBuilder(direction, properties);
  }

  public static SortBuilder from(int pageNumber, int pageSize, Sort sort) {
    return new SortBuilder(sort);
  }

  public static SortBuilder from(Pageable pageable) {
    return new SortBuilder(pageable.getSort());
  }


  public SortBuilder addOrder(Sort.Order order) {
    this.orders.add(order);
    return this;
  }

  public SortBuilder addOrderBy(String... properties) {
    return this.addOrderBy(defaultDirection, properties);
  }

  public SortBuilder addDesc(String... properties) {
    return this.addOrderBy(Sort.Direction.DESC, properties);
  }

  public SortBuilder addAsc(String... properties) {
    return this.addOrderBy(Sort.Direction.ASC, properties);
  }

  public SortBuilder addOrderBy(Sort.Direction direction, String... properties) {
    for (String property : properties) {
      addOrder(new Sort.Order(direction, property));
    }
    return this;
  }

  public Sort build() {
    return Sort.by(orders);
  }
}
