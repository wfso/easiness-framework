package cn.ibestcode.easiness.exception.handler;

import cn.ibestcode.easiness.exception.properties.EasinessExceptionTipsProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends AbstractEasinessExceptionHandler {
  @Autowired
  private EasinessExceptionTipsProperties properties;

  @Setter
  @Getter
  @ToString
  private class Error {
    private String defaultMessage;
    private String objectName;
    private String field;
    private Object rejectedValue;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
    Map<String, Object> result = new HashMap<>();
    List<Error> errors = new ArrayList<>();
    List<ObjectError> objectErrors = exception.getBindingResult().getAllErrors();
    for (ObjectError objectError : objectErrors) {
      Error error = new Error();
      BeanUtils.copyProperties(objectError, error);
      errors.add(error);
    }
    result.put(properties.getErrorName(), errors);
    result.put(properties.getCodeName(), "MethodArgumentNotValid");
    result.put(properties.getMsgName(), "参数校验失败");
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
