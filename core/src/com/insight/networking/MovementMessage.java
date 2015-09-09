package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/8/15.
 */
public class MovementMessage extends Message {
  public MovementMessage(String jsonString) {
    super(Message.MOVEMENT, jsonString, NetworkingStore.instance().getToken());
  }
}
