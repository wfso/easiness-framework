package cn.ibestcode.easiness.core.base.controller;


import cn.ibestcode.easiness.core.paging.PageableGenerator;
import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import org.springframework.data.domain.Page;

public interface Controller<T, ID, FG extends FilterGenerator, PG extends PageableGenerator> {
  T add(T t);

  T edit(T t);

  T remove(ID id);

  T info(ID id);

  Page<T> postPage(FG filterGenerator, PG pageableGenerator);

  Page<T> getPage(FG filterGenerator, PG pageableGenerator);
}
