package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class Message {
  // Message type constants
  public static final int MOVEMENT = 0;
  public static final int INITIATION = 1;

  private int type;
  private String jsonString;
  private NetworkingToken token;

  public Message(int type, String jsonString) {
    this.jsonString = jsonString;
  }

  public Message(int type, String jsonString, NetworkingToken token) {
    this.jsonString = jsonString;
    this.token = token;
  }

  public String getJson() {
    return jsonString;
  }

  public String setJson() {
    this.jsonString = jsonString;
    return jsonString;
  }

  public NetworkingToken getToken() {
    return token;
  }

  public NetworkingToken setToken(NetworkingToken token) {
    this.token = token;
    return token;
  }

  public int getType() {
    return type;
  }

  public int setType(int type) {
    this.type = type;
    return type;
  }
}
