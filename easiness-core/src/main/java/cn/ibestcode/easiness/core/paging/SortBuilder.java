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

  public static SortBuilder from() {
    return new SortBuilder();
  }

  public static SortBuilder from(Sort.Direction direction, String... properties) {
    SortBuilder builder = new SortBuilder();
    builder.addOrderBy(direction, properties);
    return builder;
  }

  public static SortBuilder from(Sort sort) {
    SortBuilder builder = new SortBuilder();
    for (Sort.Order order : sort) {
      builder.addOrder(order);
    }
    return builder;
  }

  public static SortBuilder from(Pageable pageable) {
    return from(pageable.getSort());
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
