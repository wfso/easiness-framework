package cn.ibestcode.easiness.utils.exception;

public class IdNumberException extends RuntimeException {

  public IdNumberException() {
  }

  public IdNumberException(String message) {
    super(message);
  }

  public IdNumberException(Throwable cause) {
    super(cause);
  }

  public IdNumberException(String message, Throwable cause) {
    super(message, cause);
  }
}
