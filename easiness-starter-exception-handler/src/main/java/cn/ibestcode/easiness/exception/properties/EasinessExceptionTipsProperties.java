package cn.ibestcode.easiness.exception.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties("easiness.exception.tips")
public class EasinessExceptionTipsProperties {
  private String codeName = "code";
  private String msgName = "msg";
  private String errorName = "errors";
}
