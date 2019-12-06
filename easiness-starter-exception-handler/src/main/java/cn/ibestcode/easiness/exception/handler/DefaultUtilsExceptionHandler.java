package cn.ibestcode.easiness.exception.handler;

import cn.ibestcode.easiness.exception.properties.EasinessExceptionTipsProperties;
import cn.ibestcode.easiness.utils.exception.UtilsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class DefaultUtilsExceptionHandler extends AbstractEasinessExceptionHandler {
  @Autowired
  private EasinessExceptionTipsProperties properties;

  @ExceptionHandler(UtilsException.class)
  @ResponseBody
  public ResponseEntity<Object> defaultUtilsExceptionHandler(UtilsException exception) {
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
    result.put(properties.getCodeName(), code);
    result.put(properties.getMsgName(), msg);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
