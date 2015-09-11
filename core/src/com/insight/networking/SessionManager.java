package com.insight.networking;

import io.socket.client.*;
import io.socket.emitter.Emitter;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.net.URISyntaxException;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class SessionManager {
  private static SessionManager instance = null;

  Socket socket;
  static String serverAddress = "http://localhost:3000";

  private SessionManager() {
    try {
      socket = IO.socket(serverAddress);
      socket.connect();
      socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
        @Override
        public void call(Object[] objects) {
          System.out.println("Client successfully connected to socket server.");
        }
      });
    } catch(URISyntaxException e) {
      System.out.println(e.getMessage());
    }
  }

  public static SessionManager instance() {
    if(instance == null)
      instance = new SessionManager();
    return instance;
  }

  public void send(Message message) {
    socket.emit(Integer.toString(message.getType()), message.getJson());
    System.out.println("Sent message to server... (Type: " +
      Integer.toString(message.getType()) + ", Message: " +
      message.getJson().toJSONString() + ")");
  }
}
