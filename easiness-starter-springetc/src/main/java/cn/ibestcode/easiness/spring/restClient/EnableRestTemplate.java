package cn.ibestcode.easiness.spring.restClient;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RestTemplateConfiguration.class)
public @interface EnableRestTemplate {
}
