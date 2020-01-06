package cn.ibestcode.easiness.storage.local.provider;

import cn.ibestcode.easiness.configuration.EasinessConfiguration;
import cn.ibestcode.easiness.storage.Storage;
import cn.ibestcode.easiness.storage.StorageResult;
import cn.ibestcode.easiness.storage.exception.StorageException;
import cn.ibestcode.easiness.storage.local.LocalStorageConstant;
import cn.ibestcode.easiness.storage.local.properties.LocalStorageProperties;
import cn.ibestcode.easiness.storage.provider.StorageProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class LocalStorageProvider implements StorageProvider {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired(required = false)
  private EasinessConfiguration configuration;

  @Autowired
  private LocalStorageProperties storageProperties;

  private Storage storage;

  @Override
  public Storage getStorage() {
    if (storage == null) {
      String filePath = null, prefixUrl = null;
      if (configuration != null) {
        filePath = configuration.getConfig("easiness.storage.local.filePath");
        prefixUrl = configuration.getConfig("easiness.storage.local.prefixUrl");
      }
      if (StringUtils.isEmpty(filePath)) {
        filePath = storageProperties.getFilePath();
      }

      if (StringUtils.isEmpty(prefixUrl)) {
        prefixUrl = storageProperties.getPrefixUrl();
      }
      storage = new LocalStorage(filePath, prefixUrl);
    }
    return storage;
  }

  @Override
  public void clear() {
    storage = null;
  }

  @Override
  public String getType() {
    return LocalStorageConstant.LOCAL_TYPE;
  }

  @Slf4j
  private static class LocalStorage implements Storage {

    private final String filePath;
    private final String prefixUrl;

    public LocalStorage(String filePath, String prefixUrl) {
      this.filePath = filePath;
      this.prefixUrl = prefixUrl;
    }

    @Override
    public StorageResult putFile(String fileName, InputStream inputStream) {
      String name = filePath + fileName;
      File file = new File(name);
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
      try {
        Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        LocalStorageResult result = new LocalStorageResult();
        result.setId(fileName);
        result.setSuccess(true);
        result.setUrl(prefixUrl + fileName);
        result.setPath(name);
        return result;
      } catch (IOException e) {
        log.warn(e.getMessage(), e);
        throw new StorageException("IOException", e);
      }
    }

    @Override
    public StorageResult simulatePutFile(String fileName) {
      LocalStorageResult result = new LocalStorageResult();
      result.setId(fileName);
      result.setSuccess(false);
      result.setUrl(prefixUrl + fileName);
      result.setPath(filePath + fileName);
      return result;
    }

    @Override
    public boolean removeFile(String id) {
      File file = new File(filePath + id);
      if (!file.exists() || !file.isFile()) {
        return false;
      }
      return file.delete();
    }
  }

  @Getter
  @Setter
  @ToString
  private static class LocalStorageResult implements StorageResult {
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
