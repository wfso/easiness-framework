package cn.ibestcode.easiness.exception.annotation;

import cn.ibestcode.easiness.exception.EasinessExceptionConfiguration;
import cn.ibestcode.easiness.exception.handler.MethodArgumentNotValidExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
  MethodArgumentNotValidExceptionHandler.class,
  EasinessExceptionConfiguration.class
})
public @interface EnableMethodArgumentNotValidExceptionHandler {
}
