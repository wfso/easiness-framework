package cn.ibestcode.easiness.core.base.model;

public interface UuidModel<ID> extends Model<ID> {
  String getUuid();

  void setUuid(String uuid);
}
