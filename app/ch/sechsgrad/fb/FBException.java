package ch.sechsgrad.fb;

import java.io.UnsupportedEncodingException;

public class FBException extends Exception {
  public FBException(String reason) {
    super(reason);
  }

  public FBException(String reason, Throwable e) {
    super(reason, e);
  }
}
