package com.insight.networking;

import org.json.simple.JSONObject;

public class InitiationMessage extends Message {
  public InitiationMessage() {
    super(Message.INITIATION, messageString(), NetworkingStore.instance().getToken());
  }

  public static JSONObject messageString() {
    JSONObject obj = new JSONObject();
    // No values needed to be passed through initiation message

    return obj;
  }
}
