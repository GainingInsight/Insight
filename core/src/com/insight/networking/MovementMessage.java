package com.insight.networking;

import org.json.simple.JSONObject;

public class MovementMessage extends Message {
  public MovementMessage(int playerX, int playerY) {
    super(Message.MOVEMENT, messageJson(playerX, playerY), NetworkingStore.instance().getToken());
  }

  public static JSONObject messageJson(int playerX, int playerY) {
    JSONObject obj = new JSONObject();
    obj.put("x", new Integer(playerX));
    obj.put("y", new Integer(playerY));

    return obj;
  }
}
