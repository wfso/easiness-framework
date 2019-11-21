package cn.ibestcode.easiness.exception.handler;

import cn.ibestcode.easiness.core.exception.EasinessException;
import cn.ibestcode.easiness.exception.properties.EasinessExceptionTipsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultEasinessExceptionHandler extends AbstractEasinessExceptionHandler {
  @Autowired
  private EasinessExceptionTipsProperties properties;

  @ExceptionHandler(EasinessException.class)
  @ResponseBody
  ResponseEntity<Object> defaultEasinessExceptionHandler(EasinessException throwable) {
    return commonExceptionHandler(throwable, properties.getCodeName(), properties.getMsgName());
  }
}
