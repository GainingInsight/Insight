package com.insight.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.concurrent.Callable;

/**
 * Created by jamesyanyuk on 9/6/15.
 */

public class AuthenticationManager {
  private static AuthenticationManager instance = null;

  NetworkingStore networkingStore;
  SessionManager sessionManager;

  private AuthenticationManager() {
    networkingStore = NetworkingStore.instance();
    sessionManager = SessionManager.instance();
  }

  public static AuthenticationManager instance() {
    if(instance == null)
      instance = new AuthenticationManager();
    return instance;
  }

  public void login(final String sessionId, String sessionKey, final Callable<Void> onSuccess, final Callable<Void> onFailure) throws InvalidCredentialsException, NetworkConnectionException {
    // Make request to the server for an access token
    // If 500 returned, throw an InvalidCredentialsException
    // Otherwise, store the returned token in the NetworkingStore
    HttpRequestBuilder requestBuilder = new HttpRequestBuilder();

    String content = "session_id=" + sessionId + "&session_key=" + sessionKey;

    final Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://localhost:3000/session/auth/login").content(content).build();

    try {
      Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
          System.out.println(httpResponse.getStatus().getStatusCode());
          switch (httpResponse.getStatus().getStatusCode()) {
            case 404:
              throw new RuntimeException(new NetworkConnectionException());
            case 500:
              try {
                onFailure.call();
              } catch(Exception e) {}

              throw new RuntimeException(new InvalidCredentialsException());
            case 200:
              System.out.println("Received 200");

              // Extract token from json response
              String jsonResponseStringRaw = httpResponse.getResultAsString().replace("\\", "");
              String jsonResponseString = jsonResponseStringRaw.substring(1, jsonResponseStringRaw.length() - 1);
              JSONObject jsonResponse = (JSONObject) JSONValue.parse(jsonResponseString);

              System.out.println("Sending -> " + jsonResponse.toJSONString());

              // Store the received token
              String tokenString = (String) jsonResponse.get("token");
              NetworkingToken token = new NetworkingToken(tokenString);
              networkingStore.setToken(token);

              System.out.println("Sending initiation message to server...");

              // Initiate connection to server
              sessionManager.startSocketServer(networkingStore.getToken());
              Message initMessage = new InitiationMessage();
              sessionManager.listenForSessionStart(onSuccess);
              sessionManager.send(initMessage);
            default:
              throw new RuntimeException(new NetworkConnectionException());
          }
        }

        @Override
        public void failed(Throwable t) {
          throw new RuntimeException(t);
        }

        @Override
        public void cancelled() {
        }
      });
    } catch(RuntimeException e) {
      System.out.println("---> " + e.getCause().getMessage());
    }
  }

}


