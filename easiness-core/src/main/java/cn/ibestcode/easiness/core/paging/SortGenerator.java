package cn.ibestcode.easiness.core.paging;

import org.springframework.data.domain.Sort;

public interface SortGenerator {
  Sort generateSort();
}
