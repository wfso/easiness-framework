package cn.ibestcode.easiness.utils.exception;

public class UtilsException extends RuntimeException {

  private String[] params;

  public UtilsException(String message) {
    super(message);
    this.params = new String[]{};
  }

  public UtilsException(String message, String... params) {
    super(message);
    this.params = params;
  }

  public UtilsException(String message, Throwable cause) {
    super(message, cause);
    this.params = new String[]{};
  }

  public UtilsException(String message, Throwable cause, String... params) {
    super(message, cause);
    this.params = params;
  }

  public String[] getParams() {
    return params;
  }
}
