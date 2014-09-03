package com.tkmtwo.kavro.serde;




public class SerDeException extends RuntimeException {
  
  public SerDeException(String message, Throwable cause) {
    super(message, cause);
  }
  public SerDeException(Throwable cause) {
    super(cause);
  }
}
