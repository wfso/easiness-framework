package cn.ibestcode.easiness.exception.annotation;


import cn.ibestcode.easiness.exception.EasinessExceptionConfiguration;
import cn.ibestcode.easiness.exception.handler.DefaultEasinessExceptionHandler;
import cn.ibestcode.easiness.exception.handler.DefaultUtilsExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
  DefaultEasinessExceptionHandler.class,
  EasinessExceptionConfiguration.class,
  DefaultUtilsExceptionHandler.class
})
public @interface EnableEasinessExceptionHandler {
}
