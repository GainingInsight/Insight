package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class Message {
  private String jsonString;
  private NetworkingToken token;

  public Message(String jsonString) {
    this.jsonString = jsonString;
  }

  public Message(String jsonString, NetworkingToken token) {
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
}
