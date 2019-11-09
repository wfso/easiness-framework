package cn.ibestcode.easiness.exception.handler;

import cn.ibestcode.easiness.core.exception.EasinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractEasinessExceptionHandler {

  @Autowired(required = false)
  protected MessageSource messageSource;

  protected ResponseEntity<Object> commonExceptionHandler(EasinessException exception, String codeName, String msgName) {
    Map<String, Object> result = new HashMap<>();
    log.warn(exception.getClass().getName());
    log.warn(exception.getMessage());
    String code = exception.getMessage();
    String msgKey = exception.getClass().getSimpleName() + "." + code;
    String msg;
    if (messageSource != null) {
      msg = messageSource.getMessage(msgKey, exception.getParams(), code, LocaleContextHolder.getLocale());
    } else {
      msg = code;
    }
    result.put(codeName, code);
    result.put(msgName, msg);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
