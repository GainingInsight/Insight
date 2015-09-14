package com.insight.networking;

import org.json.simple.JSONObject;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class Message {
  // Message type constants
  public static final int MOVEMENT = 0;
  public static final int INITIATION = 1;
  public static final int SESSION_START = 2;
  public static final int SESSION_DATA = 3;

  private int type;
  private JSONObject json;
  private NetworkingToken token;

  public Message(int type, JSONObject jsonString) {
    this.type = type;
    this.json = json;
  }

  public Message(int type, JSONObject json, NetworkingToken token) {
    this.type = type;
    this.json = json;
    this.token = token;
  }

  public JSONObject getJson() {
    return json;
  }

  public JSONObject setJson() {
    this.json = json;
    return json;
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

  public JSONObject getMessage() {
    // Message structure:
    //    data: JSONObject,
    //    token: String (if exists)
    JSONObject message = new JSONObject();

    message.put("data", json);
    if(token != null)
      message.put("token", token.getValue());

    return message;
  }
}
