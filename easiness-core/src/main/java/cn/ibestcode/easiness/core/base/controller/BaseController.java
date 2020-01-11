package cn.ibestcode.easiness.core.base.controller;

import cn.ibestcode.easiness.core.base.service.Service;
import cn.ibestcode.easiness.core.paging.PageableGenerator;
import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController<T, ID, FG extends FilterGenerator, PG extends PageableGenerator> implements Controller<T, ID, FG, PG> {
  protected abstract Service<T, ID> getService();

  @Override
  @PostMapping
  @ApiOperation("添加XXXX")
  public T add(@RequestBody T t) {
    return getService().create(t);
  }

  @Override
  @PutMapping
  @ApiOperation("修改XXXX")
  public T edit(@RequestBody T t) {
    return getService().update(t);
  }

  @Override
  @DeleteMapping("{id}")
  @ApiOperation("删除XXXX")
  public T remove(@PathVariable("id") ID id) {
    T t = getService().getById(id);
    getService().removeById(id);
    return t;
  }

  @Override
  @GetMapping("{id}")
  @ApiOperation("XXXX详细")
  public T info(@PathVariable("id") ID id) {
    return getService().getById(id);
  }

  @Override
  @PostMapping("page")
  @ApiOperation("XXXX列表-分页-POST")
  public Page<T> postPage(@RequestBody FG filterGenerator, PG pageableGenerator) {
    return getPage(filterGenerator, pageableGenerator);
  }

  @Override
  @GetMapping
  @ApiOperation("XXXX列表-分页-GET")
  public Page<T> getPage(FG filterGenerator, PG pageableGenerator) {
    return getService().getPage(filterGenerator.generateFilter(), pageableGenerator.generatePageable());
  }
}
