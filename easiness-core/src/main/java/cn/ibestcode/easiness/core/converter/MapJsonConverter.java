package cn.ibestcode.easiness.core.converter;

import java.util.Map;

public class MapJsonConverter extends AbstractJsonConverter<Map> {

  @Override
  Class<Map> getTargetClass() {
    return Map.class;
  }
}
