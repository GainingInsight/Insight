package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class InvalidCredentialsException extends Exception {
  public InvalidCredentialsException() {
    super();
  }

  public InvalidCredentialsException(String message) {
    super(message);
  }
}
