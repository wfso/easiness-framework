package cn.ibestcode.easiness.storage;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.storage.properties.StorageProperties;
import cn.ibestcode.easiness.storage.provider.StorageProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class EasinessStorage implements Storage {

  @Autowired(required = false)
  private EasinessConfiguration configuration;

  @Autowired
  private StorageProperties storageProperties;

  @Autowired(required = false)
  private RedissonClient redissonClient;

  @Autowired
  private List<StorageProvider> providers;

  private String type;

  @PostConstruct
  private void postConstruct() {
    this.type = storageProperties.getType();
    if (redissonClient != null) {
      RTopic rTopic = redissonClient.getTopic("easiness.storage.subscribe");
      rTopic.addListener(Integer.class, (s, o) -> clear());
    }
  }

  private void clear() {
    type = null;
    if (configuration != null) {
      type = configuration.getConfig("easiness.storage.type");
    }
    if (StringUtils.isEmpty(type)) {
      type = storageProperties.getType();
    }
    for (StorageProvider provider : providers) {
      provider.clear();
    }
  }

  @Override
  public StorageResult putFile(String fileName, InputStream inputStream) {
    return putFile(fileName, inputStream, type);
  }

  @Override
  public StorageResult putFile(String fileName, InputStream inputStream, String type) {
    for (StorageProvider provider : providers) {
      if (provider.supports(type)) {
        return provider.getStorage().putFile(fileName, inputStream);
      }
    }
    log.warn("No available provider was found by type [{}]", type);
    return null;
  }

  @Override
  public StorageResult simulatePutFile(String fileName) {
    return simulatePutFile(fileName, type);
  }


  @Override
  public StorageResult simulatePutFile(String fileName, String type) {
    for (StorageProvider provider : providers) {
      if (provider.supports(type)) {
        return provider.getStorage().simulatePutFile(fileName);
      }
    }
    log.warn("No available provider was found by type [{}]", type);
    return null;
  }

  @Override
  public boolean removeFile(String id) {
    return removeFile(id, type);
  }

  @Override
  public boolean removeFile(String id, String type) {
    for (StorageProvider provider : providers) {
      if (provider.supports(type)) {
        return provider.getStorage().removeFile(id);
      }
    }
    log.warn("No available provider was found by type [{}]", type);
    return false;
  }

}
