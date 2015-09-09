package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/8/15.
 */
public class InitiationMessage extends Message {
  public InitiationMessage(String jsonString) {
    super(Message.INITIATION, jsonString, NetworkingStore.instance().getToken());
  }
}
