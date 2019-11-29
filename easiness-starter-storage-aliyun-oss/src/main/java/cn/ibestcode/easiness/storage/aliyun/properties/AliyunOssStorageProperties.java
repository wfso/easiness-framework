package cn.ibestcode.easiness.storage.aliyun.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties("easiness.storage.aliyun.oss")
public class AliyunOssStorageProperties {

  private String accessKeyId;

  private String accessKeySecret;

  private String endpoint;

  private String prefixUrl;

  private String bucket;

}
