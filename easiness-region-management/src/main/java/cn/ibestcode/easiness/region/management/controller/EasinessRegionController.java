/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.controller;

import cn.ibestcode.easiness.region.management.biz.EasinessRegionBiz;
import cn.ibestcode.easiness.region.management.domain.*;
import cn.ibestcode.easiness.region.management.model.EasinessRegion;
import cn.ibestcode.easiness.region.management.service.EasinessRegionService;
import cn.ibestcode.easiness.utils.SpringBeanUtilsExt;
import cn.ibestcode.easiness.validation.groups.Create;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/21 20:13
 */
//@RestController
@RequestMapping("/api/region")
@Api(tags = "行政区域管理")
public class EasinessRegionController {
  @Autowired
  private EasinessRegionBiz regionBiz;

  @Autowired
  private EasinessRegionService regionService;

  @PostMapping
  @ApiOperation("添加行政区")
  //@RequiresPermissions("region:add")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void add(@RequestBody @Validated(Create.class) RegionCreateVo vo) {
    EasinessRegion region = new EasinessRegion();
    SpringBeanUtilsExt.copyPropertiesIgnoreEmpty(vo, region);
    regionBiz.create(region);
  }


  @PutMapping
  @ApiOperation("更新行政区名称")
  //@RequiresPermissions("region:edit")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void edit(@RequestBody @Validated(Create.class) RegionUpdateVo vo) {
    EasinessRegion region = new EasinessRegion();
    region.setCode(vo.getCode());
    region.setName(vo.getName());
    regionBiz.upadte(region);
  }

  @DeleteMapping("{code}")
  @ApiOperation("删除行政区")
  //@RequiresPermissions("region:remove")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public void remove(@PathVariable("code") String code) {
    regionBiz.removeByCode(code);
  }

  @GetMapping("/code/{code}")
  @ApiOperation("通过code获取所有可用区域")
  //@RequiresPermissions("region:fetch")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<RegionVo> listByCode(@PathVariable("code") String code) {
    return regionBiz.getListByCode(code);
  }

  @GetMapping("/level/{level}")
  @ApiOperation("通过level获取所有可用区域")
  //@RequiresPermissions("region:fetch")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public List<RegionVo> listByLevel(@PathVariable("level") String level) {
    return regionBiz.getListByLevel(level);
  }

  @GetMapping("page")
  @ApiOperation("根据条件查询区域-可分页")
  //@RequiresPermissions("region:list")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public Page<EasinessRegion> page(RegionQueryVo vo, Pageable pageable) {
    return regionService.getPage(vo.generateFilter(), pageable);
  }


  @GetMapping
  @ApiOperation("根据缓存时间，获取区域联动数据")
  //@RequiresPermissions("region:fetch")
  //@RequiresRoles(EasinessRoleConstant.SYSTEM_ROLE)
  public RegionCacheVo getRegionCache(@RequestParam("cacheAt") long cacheAt) {
    return regionBiz.getListByLevelSupportCache("", cacheAt);
  }

}
