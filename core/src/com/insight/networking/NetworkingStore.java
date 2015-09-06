package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class NetworkingStore {
  private NetworkingToken token;

  public NetworkingStore() {}

  public NetworkingStore(NetworkingToken token) {
    setToken(token);
  }

  public NetworkingToken getToken() {
    return token;
  }

  public NetworkingToken setToken(NetworkingToken token) {
    this.token = token;
    return token;
  }
}
