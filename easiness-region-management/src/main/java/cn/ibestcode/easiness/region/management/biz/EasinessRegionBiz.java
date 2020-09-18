/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */
package cn.ibestcode.easiness.region.management.biz;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.core.annotation.Biz;
import cn.ibestcode.easiness.region.management.EasinessRegionConstant;
import cn.ibestcode.easiness.region.management.domain.RegionCacheVo;
import cn.ibestcode.easiness.region.management.domain.RegionVo;
import cn.ibestcode.easiness.region.management.model.*;
import cn.ibestcode.easiness.region.management.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/21 19:32
 */
@Biz
public class EasinessRegionBiz {
  @Autowired
  private EasinessNationalityService nationalityService;

  @Autowired
  private EasinessProvinceService provinceService;

  @Autowired
  private EasinessCityService cityService;

  @Autowired
  private EasinessCountyService countyService;

  @Autowired
  private EasinessTownService townService;

  @Autowired
  private EasinessRegionService regionService;

  @Autowired
  private EasinessConfiguration configuration;

  /**
   * 添加一个区域
   *
   * @param region EasinessRegion对象
   * @return EasinessRegion 对象
   */
  @Transactional
  public EasinessRegion create(EasinessRegion region) {
    configuration.setConfig(EasinessRegionConstant.EASINESS_REGION_UPDATE_AT, String.valueOf(System.currentTimeMillis()));
    if (region.getParentId() == 0) {
      region.setLevel("");
    } else {
      EasinessRegion parentRegion = regionService.getById(region.getParentId());
      if (parentRegion.getParentId() == 0) {
        region.setLevel(parentRegion.getId().toString());
      } else {
        region.setLevel(parentRegion.getLevel() + "-" + parentRegion.getId());
      }
    }

    regionService.create(region);

    switch (region.getRegionType()) {
      case NATIONALITY: {
        createNationality(region);
        break;
      }
      case PROVINCE: {
        createProvince(region);
        break;
      }
      case CITY: {
        createCity(region);
        break;
      }
      case COUNTY: {
        createCounty(region);
        break;
      }
      case TOWN: {
        createTown(region);
        break;
      }
    }

    return region;
  }

  /**
   * 添加一个国家类型的区域对象
   *
   * @param region EasinessRegion 对象
   * @return EasinessNationality 对象
   */
  @Transactional
  public EasinessNationality createNationality(EasinessRegion region) {
    EasinessNationality nationality = new EasinessNationality();
    nationality.setCode(region.getCode());
    nationality.setName(region.getName());
    return createNationality(nationality);
  }

  /**
   * 添加一个国家类型的区域对象
   *
   * @param nationality EasinessNationality 对象
   * @return EasinessNationality 对象
   */
  @Transactional
  public EasinessNationality createNationality(EasinessNationality nationality) {
    nationality.setLevel("");
    return nationalityService.create(nationality);
  }

  /**
   * 添加一个省类型的区域对象
   *
   * @param region EasinessRegion 对象
   * @return EasinessProvince 对象
   */
  @Transactional
  public EasinessProvince createProvince(EasinessRegion region) {
    EasinessProvince province = new EasinessProvince();
    province.setCode(region.getCode());
    province.setName(region.getName());
    return createProvince(province);
  }

  /**
   * 添加一个省类型的区域对象
   *
   * @param province EasinessProvince 对象
   * @return EasinessProvince 对象
   */
  @Transactional
  public EasinessProvince createProvince(EasinessProvince province) {
    EasinessNationality nationality = nationalityService.getByCode(province.getCode().substring(0, 2));
    province.setLevel(nationality.getLevel() + "-" + nationality.getId());
    return provinceService.create(province);
  }

  /**
   * 添加一个市类型的区域对象
   *
   * @param region EasinessRegion 对象
   * @return EasinessCity 对象
   */
  @Transactional
  public EasinessCity createCity(EasinessRegion region) {
    EasinessCity city = new EasinessCity();
    city.setCode(region.getCode());
    city.setName(region.getName());
    return createCity(city);
  }

  /**
   * 添加一个市类型的区域对象
   *
   * @param city EasinessCity 对象
   * @return EasinessCity 对象
   */
  @Transactional
  public EasinessCity createCity(EasinessCity city) {
    EasinessProvince province = provinceService.getByCode(city.getCode().substring(0, 2));
    city.setLevel(province.getLevel() + "-" + province.getId());
    return cityService.create(city);
  }

  /**
   * 添加一个区县类型的区域对象
   *
   * @param region EasinessRegion 对象
   * @return EasinessCounty 对象
   */
  @Transactional
  public EasinessCounty createCounty(EasinessRegion region) {
    EasinessCounty county = new EasinessCounty();
    county.setCode(region.getCode());
    county.setName(region.getName());
    return createCounty(county);
  }

  /**
   * 添加一个区县类型的区域对象
   *
   * @param county EasinessCounty 对象
   * @return EasinessCounty 对象
   */
  @Transactional
  public EasinessCounty createCounty(EasinessCounty county) {
    EasinessCity city = cityService.getByCode(county.getCode().substring(0, 4));
    county.setLevel(city.getLevel() + "-" + city.getId());
    return countyService.create(county);
  }

  /**
   * 添加一个乡镇、街道类型的区域对象
   *
   * @param region EasinessRegion 对象
   * @return EasinessTown 对象
   */
  @Transactional
  public EasinessTown createTown(EasinessRegion region) {
    EasinessTown town = new EasinessTown();
    town.setCode(region.getCode());
    town.setName(region.getName());
    return createTown(town);
  }

  /**
   * 添加一个乡镇、街道类型的区域对象
   *
   * @param town EasinessTown 对象
   * @return EasinessTown 对象
   */
  @Transactional
  public EasinessTown createTown(EasinessTown town) {
    EasinessCounty county = countyService.getByCode(town.getCode().substring(0, 6));
    town.setLevel(county.getLevel() + "-" + county.getId());
    return townService.create(town);
  }


  /**
   * 更新区域名称
   *
   * @param region EasinessRegion对象
   * @return EasinessRegion 对象
   */
  @Transactional
  public EasinessRegion upadte(EasinessRegion region) {
    configuration.setConfig(EasinessRegionConstant.EASINESS_REGION_UPDATE_AT, String.valueOf(System.currentTimeMillis()));
    EasinessRegion EasinessRegion = regionService.getByCode(region.getCode());
    EasinessRegion.setName(region.getName());
    regionService.update(EasinessRegion);

    switch (EasinessRegion.getRegionType()) {
      case NATIONALITY: {
        updateNationality(EasinessRegion);
        break;
      }
      case PROVINCE: {
        updateProvince(EasinessRegion);
        break;
      }
      case CITY: {
        updateCity(EasinessRegion);
        break;
      }
      case COUNTY: {
        updateCounty(EasinessRegion);
        break;
      }
      case TOWN: {
        updateTown(EasinessRegion);
        break;
      }
    }
    return EasinessRegion;
  }

  /**
   * 更新国家级域名的名称
   *
   * @param region EasinessRegion 对象
   * @return EasinessNationality 对象
   */
  public EasinessNationality updateNationality(EasinessRegion region) {
    EasinessNationality nationality = nationalityService.getByCode(region.getCode());
    nationality.setName(region.getName());
    return nationalityService.update(nationality);
  }

  /**
   * 更新省级区域的名称
   *
   * @param region EasinessProvince 对象
   * @return EasinessProvince 对象
   */
  public EasinessProvince updateProvince(EasinessRegion region) {
    EasinessProvince province = provinceService.getByCode(region.getCode());
    province.setName(region.getName());
    return provinceService.update(province);
  }

  /**
   * 更新市级区域的名称
   *
   * @param region EasinessCity
   * @return EasinessCity
   */
  public EasinessCity updateCity(EasinessRegion region) {
    EasinessCity city = cityService.getByCode(region.getCode());
    city.setName(region.getName());
    return cityService.update(city);
  }

  /**
   * 更新区县级区域的名称
   *
   * @param region EasinessCounty
   * @return EasinessCounty
   */
  public EasinessCounty updateCounty(EasinessRegion region) {
    EasinessCounty county = countyService.getByCode(region.getCode());
    county.setName(region.getName());
    return countyService.update(county);
  }

  /**
   * 更新乡镇街道区域的名称
   *
   * @param region EasinessTown
   * @return EasinessTown
   */
  public EasinessTown updateTown(EasinessRegion region) {
    EasinessTown town = townService.getByCode(region.getCode());
    town.setName(region.getName());
    return townService.update(town);
  }

  /**
   * 删除code为指定值的区域
   *
   * @param code String 域名的编码
   * @return EasinessRegion 对象
   */
  public EasinessRegion removeByCode(String code) {
    EasinessRegion region = regionService.getByCode(code);
    switch (region.getRegionType()) {
      case NATIONALITY: {
        nationalityService.removeByCode(code);
        break;
      }
      case PROVINCE: {
        provinceService.removeByCode(code);
        break;
      }
      case CITY: {
        cityService.removeByCode(code);
        break;
      }
      case COUNTY: {
        countyService.removeByCode(code);
        break;
      }
      case TOWN: {
        townService.removeByCode(code);
        break;
      }
    }
    regionService.removeByCode(code);
    return region;
  }


  /**
   * 通过code获取所有可用的区域
   *
   * @param code String 区域编码
   * @return EasinessRegion 列表
   */
  public List<RegionVo> getListByCode(String code) {
    List<RegionVo> regions = regionService.getByCodeStartingWith(code);
    EasinessRegion region = regionService.getByCode(code);
    if (region.getParentId() > 0) {
      region = regionService.getById(region.getParentId());
      regions.add(new RegionVo(region));
    }
    return regions;
  }

  /**
   * 如果缓存之后更新过，则通过code获取所有可用的区域；
   * 如果缓存之后没有更新过，则返回空列表
   *
   * @param code String 区域编码
   * @param cacheAt 缓存时间
   * @return EasinessRegion 列表
   */
  public RegionCacheVo getListByCodeSupportCache(String code, long cacheAt) {
    RegionCacheVo vo = new RegionCacheVo();
    vo.setCacheAt(cacheAt);
    long regionUpdateAt = configuration.getLongConfig(EasinessRegionConstant.EASINESS_REGION_UPDATE_AT, System.currentTimeMillis());
    if (regionUpdateAt > cacheAt) {
      vo.setRegions(getListByCode(code));
      vo.setCacheAt(System.currentTimeMillis());
    }
    return vo;
  }


  /**
   * 通过level获取所有可用的区域
   *
   * @param level 区域层级
   * @return EasinessRegion 列表
   */
  public List<RegionVo> getListByLevel(String level) {
    List<RegionVo> regions = regionService.getByLevelStartingWith(level);
    long id = Long.valueOf(level.substring(level.lastIndexOf("-") + 1));
    EasinessRegion region = regionService.getById(id);
    regions.add(new RegionVo(region));
    if (region.getParentId() > 0) {
      region = regionService.getById(region.getParentId());
      regions.add(new RegionVo(region));
    }
    return regions;
  }

  /**
   * 如果缓存之后更新过，则通过level获取所有可用的区域；
   * 如果缓存之后没有更新过，则返回空列表
   *
   * @param level   区域层级
   * @param cacheAt 缓存时间
   * @return EasinessRegion 列表
   */
  public RegionCacheVo getListByLevelSupportCache(String level, long cacheAt) {
    RegionCacheVo vo = new RegionCacheVo();
    vo.setCacheAt(cacheAt);
    long regionUpdateAt = configuration.getLongConfig(EasinessRegionConstant.EASINESS_REGION_UPDATE_AT, System.currentTimeMillis());
    if (regionUpdateAt > cacheAt) {
      vo.setRegions(getListByLevel(level));
      vo.setCacheAt(System.currentTimeMillis());
    }
    return vo;
  }

  /**
   * 为省级区域设置冗余名称
   *
   * @param regionProvince 省级区域
   */
  public void setProvinceName(IRegionProvince regionProvince) {
    EasinessProvince province = provinceService.getByCode(regionProvince.getProvinceCode());
    if (province != null) {
      regionProvince.setProvinceName(province.getName());
    }
  }

  /**
   * 为市级区域设置冗余名称
   *
   * @param regionCity 市级区域
   */
  public void setCityName(IRegionCity regionCity) {
    setProvinceName(regionCity);
    EasinessCity city = cityService.getByCode(regionCity.getCityCode());
    if (city != null) {
      regionCity.setCityName(city.getName());
    }
  }

  /**
   * 为县级区域设置冗余名称
   *
   * @param regionCounty 县级区域
   */
  public void setCountyName(IRegionCounty regionCounty) {
    setCityName(regionCounty);
    EasinessCounty county = countyService.getByCode(regionCounty.getCountyCode());
    if (county != null) {
      regionCounty.setCountyName(county.getName());
    }
  }

  /**
   * 为乡镇级区域设置冗余名称
   *
   * @param regionTown 乡镇级区域
   */
  public void setTownName(IRegionTown regionTown) {
    setCountyName(regionTown);
    EasinessTown town = townService.getByCode(regionTown.getTownCode());
    if (town != null) {
      regionTown.setTownName(town.getName());
    }
  }

}
