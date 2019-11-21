package cn.ibestcode.easiness.core.paging;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PageableGenerator {
  int getPage();

  void setPage(int page);

  int getSize();

  void setSize(int size);

  Pageable generatePageable();


  default PageableGenerator addDesc(String... properties) {
    return addOrderBy(Sort.Direction.DESC, properties);
  }

  default PageableGenerator addAsc(String... properties) {
    return addOrderBy(Sort.Direction.ASC, properties);
  }

  PageableGenerator addOrderBy(Sort.Direction direction, String... properties);

}
