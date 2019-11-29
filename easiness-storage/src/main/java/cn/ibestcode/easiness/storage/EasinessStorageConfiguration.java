package cn.ibestcode.easiness.storage;

import cn.ibestcode.easiness.storage.properties.StorageProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class EasinessStorageConfiguration {

  @Bean
  @ConditionalOnMissingBean(StorageProperties.class)
  public StorageProperties storageProperties() {
    return new StorageProperties();
  }

  @Bean
  @ConditionalOnMissingBean(Storage.class)
  public EasinessStorage defaultStorage() {
    return new EasinessStorage();
  }

}
