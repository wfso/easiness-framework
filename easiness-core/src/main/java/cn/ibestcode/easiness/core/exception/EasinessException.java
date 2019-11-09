package cn.ibestcode.easiness.core.exception;

public class EasinessException extends RuntimeException {

  private String[] params;

  public EasinessException(String message) {
    super(message);
    this.params = new String[]{};
  }

  public EasinessException(String message, String[] params) {
    super(message);
    this.params = params;
  }

  public EasinessException(String message, String[] params, Throwable cause) {
    super(message, cause);
    this.params = params;
  }

  public String[] getParams() {
    return params;
  }
}
