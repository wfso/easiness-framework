package cn.ibestcode.easiness.auth.handler;

import java.lang.reflect.Method;

public interface PermissionPersistenceHandler {
  void persistencePermission(Class controllerClass, Method method);
}
