package cn.ibestcode.easiness.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractExceptionHandler {

  @Autowired(required = false)
  protected MessageSource messageSource;

  protected ResponseEntity<Object> commonExceptionHandler(Throwable throwable, String codeName, String msgName) {
    Map<String, Object> result = new HashMap<>();
    log.warn(throwable.getClass().getName());
    log.warn(throwable.getMessage());
    String code = throwable.getMessage();
    String msgKey = throwable.getClass().getSimpleName() + "." + code;
    String msg;
    if (messageSource != null) {
      msg = messageSource.getMessage(msgKey, null, code, LocaleContextHolder.getLocale());
    } else {
      msg = code;
    }
    result.put(codeName, code);
    result.put(msgName, msg);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
