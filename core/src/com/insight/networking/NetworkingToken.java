package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class NetworkingToken {
  private String tokenVal;

  public NetworkingToken() {}

  public NetworkingToken(String tokenVal) {
    setValue(tokenVal);
  }

  public String getValue() {
    return tokenVal;
  }

  public String setValue(String tokenVel) {
    this.tokenVal = tokenVal;
    return tokenVal;
  }
}
