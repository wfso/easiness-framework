package cn.ibestcode.easiness.redisson;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
  RedissonConfiguration.class,
})
public @interface EnableEasinessRedisson {
}
