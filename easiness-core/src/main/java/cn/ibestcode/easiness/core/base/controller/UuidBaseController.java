package cn.ibestcode.easiness.core.base.controller;

import cn.ibestcode.easiness.core.base.model.UuidBaseJpaModel;
import cn.ibestcode.easiness.core.base.service.UuidBaseJpaService;
import cn.ibestcode.easiness.core.paging.PageableGenerator;
import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

public abstract class UuidBaseController<T extends UuidBaseJpaModel, FG extends FilterGenerator, PG extends PageableGenerator> implements UuidController<T, FG, PG> {

  protected abstract UuidBaseJpaService<T> getService();

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
  @DeleteMapping("{uuid}")
  @ApiOperation("删除XXXX")
  public T remove(@PathVariable("uuid") String uuid) {
    T t = getService().getByUuid(uuid);
    getService().removeByUuid(uuid);
    return t;
  }

  @Override
  @GetMapping("{uuid}")
  @ApiOperation("XXXX详情")
  public T info(@PathVariable("uuid") String uuid) {
    return getService().getByUuid(uuid);
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
