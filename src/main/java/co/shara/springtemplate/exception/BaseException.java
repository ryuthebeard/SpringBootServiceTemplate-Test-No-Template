package co.shara.springtemplate.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "errorData")
public class BaseException extends RuntimeException {
  // An immutable exception base class.

  protected final  boolean clientError;
  protected final String errorCode;
  protected final Map<String, Object> errorData = new HashMap<>();

  public BaseException() {
    super();
    this.clientError = false;
    this.errorCode = "";
  }

  public BaseException(String message) {
    super(message);
    this.clientError = false;
    this.errorCode = "";
  }

  public BaseException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
    this.clientError = false;
  }

  public BaseException(String message, boolean clientError) {
    super(message);
    this.clientError = clientError;
    this.errorCode = "";
  }

  public BaseException(String message, boolean clientError, String errorCode) {
    super(message);
    this.clientError = clientError;
    this.errorCode = errorCode;
  }

  public BaseException(String message, Throwable cause) {

    super(message, cause);
    this.errorCode = "";
    this.clientError = false;
  }

  public final void addData(String key, Object value) {
    this.errorData.put(key, value);
  }
}
