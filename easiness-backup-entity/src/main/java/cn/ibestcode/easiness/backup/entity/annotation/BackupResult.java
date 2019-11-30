package cn.ibestcode.easiness.backup.entity.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BackupResult {
  String type() default "default";
}
