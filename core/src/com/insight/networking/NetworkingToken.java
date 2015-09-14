package com.insight.networking;

public class NetworkingToken {
  private String tokenVal;

  public NetworkingToken() {}

  public NetworkingToken(String tokenVal) {
    setValue(tokenVal);
  }

  public String getValue() {
    return tokenVal;
  }

  public String setValue(String tokenVal) {
    this.tokenVal = tokenVal;
    return tokenVal;
  }
}
