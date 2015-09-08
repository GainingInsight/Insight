package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class NetworkingStore {
  private NetworkingToken token;

  private static NetworkingStore instance = null;

  private NetworkingStore() {}

  private NetworkingStore(NetworkingToken token) {
    setToken(token);
  }

  public static NetworkingStore instance() {
    if(instance == null)
      instance = new NetworkingStore();
    return instance;
  }

  public static NetworkingStore instance(NetworkingToken token) {
    if(instance == null)
      instance = new NetworkingStore(token);
    return instance;
  }

  public NetworkingToken getToken() {
    return token;
  }

  public NetworkingToken setToken(NetworkingToken token) {
    this.token = token;
    return token;
  }
}
