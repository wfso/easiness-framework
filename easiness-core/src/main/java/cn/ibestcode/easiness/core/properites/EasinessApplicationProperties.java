package cn.ibestcode.easiness.core.properites;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("easiness.application")
public class EasinessApplicationProperties {
  // 应用程序标识
  private String id = "EASINESS-APPLICATION-ID";
}
