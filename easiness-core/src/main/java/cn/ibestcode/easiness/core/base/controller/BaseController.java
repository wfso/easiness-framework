package cn.ibestcode.easiness.core.base.controller;

import cn.ibestcode.easiness.core.base.service.Service;
import cn.ibestcode.easiness.core.paging.PageableGenerator;
import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController<T, ID, FG extends FilterGenerator, PG extends PageableGenerator> implements Controller<T, ID, FG, PG> {
  protected abstract Service<T, ID> getService();

  @Override
  @PostMapping
  public T add(@RequestBody T t) {
    return getService().create(t);
  }

  @Override
  @PutMapping
  public T edit(@RequestBody T t) {
    return getService().update(t);
  }

  @Override
  @DeleteMapping(path = "{id}")
  public T remove(@PathVariable("id") ID id) {
    T t = getService().getById(id);
    getService().removeById(id);
    return t;
  }

  @Override
  @GetMapping(path = "{id}")
  public T info(@PathVariable("id") ID id) {
    return getService().getById(id);
  }

  @Override
  @PostMapping(path = "page")
  public Page<T> postPage(@RequestBody FG filterGenerator, PG pageableGenerator) {
    return getPage(filterGenerator, pageableGenerator);
  }

  @Override
  @GetMapping()
  public Page<T> getPage(FG filterGenerator, PG pageableGenerator) {
    return getService().getPage(filterGenerator.generateFilter(), pageableGenerator.generatePageable());
  }
}
