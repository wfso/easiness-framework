package cn.ibestcode.easiness.storage;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.storage.exception.StorageException;
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
import java.util.Map;

@Slf4j
public class EasinessStorage implements Storage {

  @Autowired(required = false)
  protected EasinessConfiguration configuration;

  @Autowired
  protected StorageProperties storageProperties;

  @Autowired(required = false)
  protected RedissonClient redissonClient;

  @Autowired
  protected List<StorageProvider> providers;

  protected Map<String, StorageProvider> providerMap;

  protected String type;

  @PostConstruct
  protected void postConstruct() {
    this.type = storageProperties.getType();
    if (redissonClient != null) {
      RTopic rTopic = redissonClient.getTopic(EasinessStorageConstant.SUBSCRIBE_CONFIG_FIELD);
      rTopic.addListener(Integer.class, (s, o) -> clear());
    }
  }

  protected void clear() {
    type = null;
    if (configuration != null) {
      type = configuration.getConfig(EasinessStorageConstant.DEFAULT_TYPE_CONFIG_FIELD);
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
    StorageProvider provider = getProviderByType(type);
    if (provider == null) {
      throw new StorageException("StorageProviderCanNotBeNull");
    }
    return provider.getStorage().putFile(fileName, inputStream);
  }

  @Override
  public StorageResult simulatePutFile(String fileName) {
    return simulatePutFile(fileName, type);
  }


  @Override
  public StorageResult simulatePutFile(String fileName, String type) {
    StorageProvider provider = getProviderByType(type);
    if (provider == null) {
      throw new StorageException("StorageProviderCanNotBeNull");
    }
    return provider.getStorage().simulatePutFile(fileName);
  }

  @Override
  public boolean removeFile(String id) {
    return removeFile(id, type);
  }

  @Override
  public boolean removeFile(String id, String type) {
    StorageProvider provider = getProviderByType(type);
    if (provider == null) {
      throw new StorageException("StorageProviderCanNotBeNull");
    }
    return provider.getStorage().removeFile(id);
  }

  protected StorageProvider getProviderByType(String type) {
    if (providerMap == null) {
      synchronized (this) {
        if (providerMap == null) {
          for (StorageProvider provider : providers) {
            providerMap.put(provider.getType(), provider);
          }
        }
      }
    }
    if (!providerMap.containsKey(type)) {
      log.warn("No available provider was found by type [{}]", type);
      throw new StorageException("NotProviderExistOfType", type);
    }
    return providerMap.get(type);
  }

}
