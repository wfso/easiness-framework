package cn.ibestcode.easiness.storage;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EasinessStorageConfiguration.class)
public @interface EnableEasinessStorage {
}
