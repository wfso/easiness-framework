package cn.ibestcode.easiness.auth.password;

import cn.ibestcode.easiness.auth.EnableEasinessAuth;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EasinessPasswordAuthConfiguration.class})
@EnableEasinessAuth
public @interface EnableEasinessPasswordAuth {
}
