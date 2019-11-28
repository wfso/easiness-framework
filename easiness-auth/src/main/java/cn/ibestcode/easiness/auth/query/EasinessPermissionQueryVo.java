package cn.ibestcode.easiness.auth.query;

import cn.ibestcode.easiness.core.query.builder.DefaultFiltersBuilder;
import cn.ibestcode.easiness.core.query.filter.FilterGenerator;
import cn.ibestcode.easiness.core.query.filter.IFilter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class EasinessPermissionQueryVo implements FilterGenerator, Serializable {

  private String key;

  @Override
  public IFilter generateFilter() {
    return DefaultFiltersBuilder
      .getOrInstance()
      .orContain("code", key)
      .orContain("name", key)
      .orContain("intro", key)
      .build();
  }
}
