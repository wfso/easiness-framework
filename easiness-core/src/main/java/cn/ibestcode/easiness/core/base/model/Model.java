package cn.ibestcode.easiness.core.base.model;


public interface Model<ID> {

  long getCreatedAt();

  void setCreatedAt(long createdAt);

  long getUpdatedAt();

  void setUpdatedAt(long updatedAt);

  ID getId();

  void setId(ID id);

  boolean isAvailable();
}
