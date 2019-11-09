package cn.ibestcode.easiness.core.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author wfso (仵士杰)
 * #-------------------------------------------------------------------------------------------------------------#
 * #               _____                    _____                    _____                   _______             #
 * #              /\    \                  /\    \                  /\    \                 /::\    \            #
 * #             /::\____\                /::\    \                /::\    \               /::::\    \           #
 * #            /:::/    /               /::::\    \              /::::\    \             /::::::\    \          #
 * #           /:::/   _/___            /::::::\    \            /::::::\    \           /::::::::\    \         #
 * #          /:::/   /\    \          /:::/\:::\    \          /:::/\:::\    \         /:::/~~\:::\    \        #
 * #         /:::/   /::\____\        /:::/__\:::\    \        /:::/__\:::\    \       /:::/    \:::\    \       #
 * #        /:::/   /:::/    /       /::::\   \:::\    \       \:::\   \:::\    \     /:::/    / \:::\    \      #
 * #       /:::/   /:::/   _/___    /::::::\   \:::\    \    ___\:::\   \:::\    \   /:::/____/   \:::\____\     #
 * #      /:::/___/:::/   /\    \  /:::/\:::\   \:::\    \  /\   \:::\   \:::\    \ |:::|    |     |:::|    |    #
 * #     |:::|   /:::/   /::\____\/:::/  \:::\   \:::\____\/::\   \:::\   \:::\____\|:::|____|     |:::|    |    #
 * #     |:::|__/:::/   /:::/    /\::/    \:::\   \::/    /\:::\   \:::\   \::/    / \:::\    \   /:::/    /     #
 * #      \:::\/:::/   /:::/    /  \/____/ \:::\   \/____/  \:::\   \:::\   \/____/   \:::\    \ /:::/    /      #
 * #       \::::::/   /:::/    /            \:::\    \       \:::\   \:::\    \        \:::\    /:::/    /       #
 * #        \::::/___/:::/    /              \:::\____\       \:::\   \:::\____\        \:::\__/:::/    /        #
 * #         \:::\__/:::/    /                \::/    /        \:::\  /:::/    /         \::::::::/    /         #
 * #          \::::::::/    /                  \/____/          \:::\/:::/    /           \::::::/    /          #
 * #           \::::::/    /                                     \::::::/    /             \::::/    /           #
 * #            \::::/    /                                       \::::/    /               \::/____/            #
 * #             \::/____/                                         \::/    /                 ~~                  #
 * #              ~~                                                \/____/                                      #
 * #-------------------------------------------------------------------------------------------------------------#
 * create by WFSO (仵士杰) at 2019/7/26 11:06
 */
public class PageableBuilder {
  private int pageNumber;
  private int pageSize;
  private SortBuilder sortBuilder;

  private static Sort.Direction defaultDirection = Sort.Direction.ASC;

  private PageableBuilder() {
    this.pageNumber = 0;
    this.pageSize = 20;
    sortBuilder = SortBuilder.from();
  }

  private PageableBuilder(int pageNumber, int pageSize) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    sortBuilder = SortBuilder.from();
  }

  private PageableBuilder(int pageNumber, int pageSize, Sort.Order... orders) {
    this(pageNumber, pageSize);
    for (Sort.Order order : orders) {
      sortBuilder.addOrder(order);
    }
  }

  private PageableBuilder(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
    this(pageNumber, pageSize);
    for (String property : properties) {
      sortBuilder.addOrder(new Sort.Order(direction, property));
    }
  }

  private PageableBuilder(int pageNumber, int pageSize, Sort sort) {
    this(pageNumber, pageSize);
    for (Sort.Order order : sort) {
      sortBuilder.addOrder(order);
    }
  }


  public static PageableBuilder from() {
    return new PageableBuilder();
  }

  public static PageableBuilder from(int pageNumber, int pageSize) {
    return new PageableBuilder(pageNumber, pageSize);
  }


  public static PageableBuilder from(int pageNumber, int pageSize, Sort.Direction direction, String properties) {
    return new PageableBuilder(pageNumber, pageSize, direction, properties);
  }

  public static PageableBuilder from(int pageNumber, int pageSize, Sort sort) {
    return new PageableBuilder(pageNumber, pageSize, sort);
  }

  public static PageableBuilder from(Pageable pageable) {
    return new PageableBuilder(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
  }

  public PageableBuilder setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
    return this;
  }

  public PageableBuilder setPageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public PageableBuilder addOrder(Sort.Order order) {
    sortBuilder.addOrder(order);
    return this;
  }

  public PageableBuilder addOrderBy(String... properties) {
    return this.addOrderBy(defaultDirection, properties);
  }

  public PageableBuilder addDesc(String... properties) {
    return this.addOrderBy(Sort.Direction.DESC, properties);
  }

  public PageableBuilder addAsc(String... properties) {
    return this.addOrderBy(Sort.Direction.ASC, properties);
  }

  public PageableBuilder addOrderBy(Sort.Direction direction, String... properties) {
    for (String property : properties) {
      addOrder(new Sort.Order(direction, property));
    }
    return this;
  }


  public Pageable build() {
    return PageRequest.of(pageNumber, pageSize, sortBuilder.build());
  }
}
