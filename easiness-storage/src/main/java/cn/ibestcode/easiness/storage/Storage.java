package cn.ibestcode.easiness.storage;

import java.io.InputStream;

public interface Storage {
  StorageResult putFile(String fileName, InputStream inputStream);

  default StorageResult putFile(String fileName, InputStream inputStream, String type) {
    return putFile(fileName, inputStream);
  }

  StorageResult simulatePutFile(String fileName);

  default StorageResult simulatePutFile(String fileName, String type) {
    return simulatePutFile(fileName);
  }

  boolean removeFile(String id);

  default boolean removeFile(String id, String type) {
    return removeFile(id);
  }

}
