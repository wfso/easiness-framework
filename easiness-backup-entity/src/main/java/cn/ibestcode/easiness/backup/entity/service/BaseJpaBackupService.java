package cn.ibestcode.easiness.backup.entity.service;

import cn.ibestcode.easiness.core.base.model.BaseJpaModel;

public abstract class BaseJpaBackupService<T extends BaseJpaModel> extends BaseBackupService <T, Long> {
}
