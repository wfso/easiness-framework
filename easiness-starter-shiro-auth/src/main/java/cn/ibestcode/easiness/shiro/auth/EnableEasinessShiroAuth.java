package cn.ibestcode.easiness.shiro.auth;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
  EasinessShiroAuthConfiguration.class
})
public @interface EnableEasinessShiroAuth {
}
