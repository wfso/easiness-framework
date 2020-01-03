/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.configuration.controller;

import cn.ibestcode.easiness.configuration.model.Configuration;
import cn.ibestcode.easiness.configuration.service.ConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/23
 */
@Api(tags = "系统配置管理接口")
@RequestMapping("/api/easiness/configuration")
public class ConfigurationController {

  @Autowired
  private ConfigurationService configurationService;

  @PostMapping
  @ApiOperation("添加一个配置项")
  public void add(@RequestBody Configuration configuration) {
    configurationService.create(configuration);
  }

  @PutMapping
  @ApiOperation("修改一个配置项")
  public void edit(@RequestBody Configuration configuration) {
    configurationService.update(configuration);
  }

  @PutMapping("batch")
  @ApiOperation("批量配置")
  public void batchUpdate(@RequestBody List<Configuration> configs) {
    configurationService.update(configs);
  }

  @DeleteMapping("/{id}")
  @ApiOperation("通过id删除一个配置项")
  public void remove(@PathVariable("id") long id) {
    configurationService.removeById(id);
  }

  @DeleteMapping("/key/{key}")
  @ApiOperation("通过key删除一个配置项")
  public void remove(@PathVariable("key") String key) {
    configurationService.remove(key);
  }

  @GetMapping("/{category}")
  @ApiOperation("通过配置项的分类,获取一组配置项")
  public List<Configuration> getByCategory(@PathVariable("category") String category) {
    return configurationService.getAllByCategory(category);
  }


  @GetMapping
  @ApiOperation("获取所有配置项")
  public List<Configuration> getAll() {
    return configurationService.getAll();
  }


}
