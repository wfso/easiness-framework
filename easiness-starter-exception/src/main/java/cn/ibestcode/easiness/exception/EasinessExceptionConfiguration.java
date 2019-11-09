package cn.ibestcode.easiness.exception;

import cn.ibestcode.easiness.exception.properties.EasinessExceptionTipsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EasinessExceptionConfiguration {
  @Bean
  @ConditionalOnMissingBean
  public EasinessExceptionTipsProperties easinessExceptionTipsProperties() {
    return new EasinessExceptionTipsProperties();
  }
}
