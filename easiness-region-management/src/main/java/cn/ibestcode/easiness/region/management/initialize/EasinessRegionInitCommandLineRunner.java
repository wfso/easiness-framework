/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.initialize;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.region.management.EasinessRegionConstant;
import cn.ibestcode.easiness.region.management.biz.EasinessRegionBiz;
import cn.ibestcode.easiness.region.management.model.EasinessRegion;
import cn.ibestcode.easiness.region.management.service.EasinessRegionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/01/21 20:13
 */
@Slf4j
public class EasinessRegionInitCommandLineRunner implements CommandLineRunner {
  @Autowired
  private EasinessRegionBiz regionBiz;

  @Autowired
  private EasinessRegionService regionService;

  @Autowired
  private EasinessConfiguration configuration;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public void init() throws IOException {
    EasinessRegion region = regionService.getByCode("");
    if (region == null || region.getId() == null || region.getId() == 0) {
      log.info("easiness region initializing …………");

      InputStream inputStream = getClass().getClassLoader()
        .getResourceAsStream("region.data");
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = reader.readLine()) != null) {
        if (StringUtils.isBlank(line)) {
          continue;
        }
        region = objectMapper.readValue(line, EasinessRegion.class);

        EasinessRegion easinessRegion = regionService.getByCode(region.getCode());
        if (easinessRegion == null || easinessRegion.getId() == null || easinessRegion.getId() == 0) {

          String parentCode = "";

          switch (region.getRegionType()) {
            case NATIONALITY: {
              break;
            }
            case PROVINCE: {
              parentCode = region.getCode().substring(0, 2);
              break;
            }
            case CITY: {
              parentCode = region.getCode().substring(0, 4);
              break;
            }
            case COUNTY: {
              parentCode = region.getCode().substring(0, 6);
              break;
            }
            case TOWN: {
              parentCode = region.getCode().substring(0, 8);
              break;
            }
          }
          EasinessRegion parent = regionService.getByCode(parentCode);
          if (parent != null && parent.getId() != null && parent.getId() > 0) {
            region.setParentId(parent.getId());
          } else {
            log.warn("parent not found : " + region.toString());
          }
          regionBiz.create(region);
        } else {
          log.warn("the code already exist " + region.toString());
        }
      }
      reader.close();
      inputStream.close();

      // 更新缓存时间
      configuration.setConfig(EasinessRegionConstant.EASINESS_REGION_UPDATE_AT, String.valueOf(System.currentTimeMillis()));

    } else {
      log.info("easiness region is initialized");
    }
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("========== EasinessRegionInitCommandLineRunner Started ==========");
    init();
    log.info("========== EasinessRegionInitCommandLineRunner Ended ==========");
  }
}
