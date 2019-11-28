package cn.ibestcode.easiness.auth.handler;

import java.lang.reflect.Method;

public interface RolePersistenceHandler {
  void persistenceRole(Class controllerClass, Method method);
}
