package cn.ibestcode.easiness.core.base.controller;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.core.paging.PageableGenerator;
import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

public abstract class UuidBaseController<T extends UuidBaseJpaModel, FG extends FilterGenerator, PG extends PageableGenerator> implements UuidController<T, FG, PG> {

  protected abstract UuidBaseJpaService<T> getService();

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
  @DeleteMapping(path = "{uuid}")
  public T remove(@PathVariable("uuid") String uuid) {
    T t = getService().getByUuid(uuid);
    getService().removeByUuid(uuid);
    return t;
  }

  @Override
  @GetMapping(path = "{uuid}")
  public T info(@PathVariable("uuid") String uuid) {
    return getService().getByUuid(uuid);
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
