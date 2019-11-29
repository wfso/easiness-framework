package cn.ibestcode.easiness.storage;

public interface StorageResult {
  String getId();

  boolean isSuccess();

  String toJSON();

  String getUrl();

  String getPath();
}
