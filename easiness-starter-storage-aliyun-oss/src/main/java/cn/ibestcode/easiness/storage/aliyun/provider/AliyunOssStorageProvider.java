package cn.ibestcode.easiness.storage.aliyun.provider;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.storage.Storage;
import cn.ibestcode.easiness.storage.StorageResult;
import cn.ibestcode.easiness.storage.aliyun.AliyunOssStorageConstant;
import cn.ibestcode.easiness.storage.aliyun.properties.AliyunOssStorageProperties;
import cn.ibestcode.easiness.storage.exception.StorageException;
import cn.ibestcode.easiness.storage.provider.StorageProvider;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.InputStream;

@Slf4j
@Component
public class AliyunOssStorageProvider implements StorageProvider {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired(required = false)
  private EasinessConfiguration configuration;

  @Autowired
  private AliyunOssStorageProperties storageProperties;

  private Storage storage;

  @Override
  public Storage getStorage() {
    if (storage == null) {
      String endpoint = null, prefixUrl = null, accessKeyId = null, accessKeySecret = null, bucket = null;
      if (configuration != null) {
        endpoint = configuration.getConfig("easiness.storage.aliyun.oss.endpoint");
        prefixUrl = configuration.getConfig("easiness.storage.aliyun.oss.prefixUrl");
        accessKeyId = configuration.getConfig("seasiness.storage.aliyun.oss.accessKeyId");
        accessKeySecret = configuration.getConfig("easiness.storage.aliyun.oss.accessKeySecret");
        bucket = configuration.getConfig("easiness.storage.aliyun.oss.bucket");
      }
      if (StringUtils.isEmpty(endpoint)) {
        endpoint = storageProperties.getEndpoint();
        Assert.isTrue(!StringUtils.isEmpty(endpoint), "请配置阿里云OSS存储");
      }

      if (StringUtils.isEmpty(prefixUrl)) {
        prefixUrl = storageProperties.getPrefixUrl();
        Assert.isTrue(!StringUtils.isEmpty(prefixUrl), "请配置阿里云OSS存储");
      }

      if (StringUtils.isEmpty(accessKeyId)) {
        accessKeyId = storageProperties.getAccessKeyId();
        Assert.isTrue(!StringUtils.isEmpty(accessKeyId), "请配置阿里云OSS存储");
      }

      if (StringUtils.isEmpty(accessKeySecret)) {
        accessKeySecret = storageProperties.getAccessKeySecret();
        Assert.isTrue(!StringUtils.isEmpty(accessKeySecret), "请配置阿里云OSS存储");
      }

      if (StringUtils.isEmpty(bucket)) {
        bucket = storageProperties.getBucket();
        Assert.isTrue(!StringUtils.isEmpty(bucket), "请配置阿里云OSS存储");
      }
      storage = new AliyunOssStorage(accessKeyId, accessKeySecret, prefixUrl, endpoint, bucket);
    }

    return storage;
  }

  @Override
  public void clear() {
    storage = null;
  }

  @Override
  public String getType() {
    return AliyunOssStorageConstant.ALIYUN_OSS_TYPE;
  }

  private class AliyunOssStorage implements Storage {
    private final String endpoint;
    private final String bucket;
    private final String prefixUrl;

    private final OSSClient ossClient;

    public AliyunOssStorage(String accessKeyId, String accessKeySecret, String prefixUrl, String endpoint, String bucket) {
      this.bucket = bucket;
      this.prefixUrl = prefixUrl;
      if (StringUtils.isEmpty(endpoint)) {
        this.endpoint = prefixUrl;
      } else {
        this.endpoint = endpoint;
      }
      ossClient = new OSSClient(this.endpoint, new DefaultCredentialProvider(accessKeyId, accessKeySecret), new ClientConfiguration());
    }

    @Override
    public StorageResult putFile(String fileName, InputStream inputStream) {
      ossClient.putObject(this.bucket, fileName, inputStream);
      AliyunOSSStorageResult result = new AliyunOSSStorageResult();
      result.setId(fileName);
      result.setSuccess(true);
      result.setUrl(this.prefixUrl + fileName);
      return result;
    }

    @Override
    public StorageResult simulatePutFile(String fileName) {
      AliyunOSSStorageResult result = new AliyunOSSStorageResult();
      result.setId(fileName);
      result.setSuccess(false);
      result.setUrl(this.prefixUrl + fileName);
      return result;
    }

    @Override
    public boolean removeFile(String id) {
      ossClient.deleteObject(this.bucket, id);
      return true;
    }
  }

  @Getter
  @Setter
  @ToString
  private class AliyunOSSStorageResult implements StorageResult {
    private String id;
    private boolean success;
    private String url;
    private String path;

    @Override
    public String toJSON() {
      try {
        return objectMapper.writeValueAsString(this);
      } catch (JsonProcessingException e) {
        log.warn(e.getMessage(), e);
        throw new StorageException("ConvertJSONFail", e);
      }
    }

    @Override
    public String toString() {
      return toJSON();
    }
  }
}
