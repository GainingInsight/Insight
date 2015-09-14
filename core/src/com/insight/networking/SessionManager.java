package com.insight.networking;

import com.badlogic.gdx.Gdx;
import io.socket.client.*;
import io.socket.emitter.Emitter;
import org.json.*;

import java.net.URISyntaxException;
import java.util.concurrent.Callable;

public class SessionManager {
  private static SessionManager instance = null;

  Socket socket;
  static String serverAddress = "http://localhost:3000";

  public String title;
  public String description;
  public String sessionId;
  public String sessionKey;
  public String clientRole;

  private SessionManager() {}

  public static SessionManager instance() {
    if(instance == null)
      instance = new SessionManager();
    return instance;
  }

  public void startSocketServer(NetworkingToken token) {
    try {
      IO.Options socketOptions = new IO.Options();
      socketOptions.forceNew = true;
      socketOptions.query = "token=" + token.getValue();
        socket = IO.socket(serverAddress, socketOptions);
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

  public void setSessionData(JSONObject obj) {
    try {
      title = (String) obj.get("title");
      description = (String) obj.get("description");
      sessionId = (String) obj.get("sessionId");
      sessionKey = (String) obj.get("sessionKey");
      clientRole = (String) obj.get("clientRole");
    } catch(JSONException e) {
      System.out.println(e.getMessage());
    }
  }

  public void listenForSessionStart(Callable<Void> nextScreen) {
    // Call the next screen once game has begun
    final Callable<Void> nextScreenFinal = nextScreen;

    socket.on(Integer.toString(Message.SESSION_DATA), new Emitter.Listener() {
      @Override
      public void call(Object[] objects) {
        // Received initial session data
        JSONObject obj = (JSONObject)objects[0];
        setSessionData(obj);
      }
    });

    socket.on(Integer.toString(Message.SESSION_START), new Emitter.Listener() {
      @Override
      public void call(Object[] objects) {
        // All players ready, game session will now start
        Gdx.app.postRunnable(new Runnable() {
          @Override
          public void run() {
            try {
              nextScreenFinal.call();
            } catch(Exception e) {
              System.out.println(e.getMessage());
            }
          }
        });
      }
    });
  }

  public void send(Message message) {
    socket.emit(Integer.toString(message.getType()), message.getMessage());
    System.out.println("Sent message to server... (Type: " +
      Integer.toString(message.getType()) + ", Message: " +
      message.getJson().toJSONString() + ")");
  }
}
