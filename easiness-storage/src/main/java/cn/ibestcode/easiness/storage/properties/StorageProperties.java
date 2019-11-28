package cn.ibestcode.easiness.storage.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties("easiness.storage")
public class StorageProperties {
  private String type;
}
