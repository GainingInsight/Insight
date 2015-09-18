package com.insight.networking;

import org.json.simple.JSONObject;

public class MovementMessage extends Message {
  public MovementMessage(int key, boolean pressed, float playerX, float playerY) {
    super(Message.MOVEMENT_SET, messageJson(key, pressed, (int)playerX, (int)playerY), NetworkingStore.instance().getToken());
  }

  public static JSONObject messageJson(int key, boolean pressed, int playerX, int playerY) {
    JSONObject obj = new JSONObject();

    obj.put("playerX", new Integer(playerX));
    obj.put("playerY", new Integer(playerY));

    obj.put("key", new Integer(key));
    obj.put("pressed", new Boolean(pressed));

    return obj;
  }
}
