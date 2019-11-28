package cn.ibestcode.easiness.spring.restClient;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class MultiValueMapUtil {
  public static MultiValueMap fromMap(Map map){
    MultiValueMap multiValueMap = new LinkedMultiValueMap();
    multiValueMap.setAll(map);
    return multiValueMap;
  }
}
