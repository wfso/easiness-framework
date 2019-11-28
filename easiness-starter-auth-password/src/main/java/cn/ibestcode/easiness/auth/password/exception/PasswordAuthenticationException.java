package cn.ibestcode.easiness.auth.password.exception;


import cn.ibestcode.easiness.auth.exception.EasinessAuthenticationException;

public class PasswordAuthenticationException extends EasinessAuthenticationException {

  public PasswordAuthenticationException(String message) {
    super(message);
  }

  public PasswordAuthenticationException(String message, String... params) {
    super(message, params);
  }

  public PasswordAuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }

  public PasswordAuthenticationException(String message, Throwable cause, String... params) {
    super(message, cause, params);
  }
}
