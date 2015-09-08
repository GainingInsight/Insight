package com.insight.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class AuthenticationManager {
  private static AuthenticationManager instance = null;

  NetworkingStore networkingStore;

  private AuthenticationManager() {
    networkingStore = NetworkingStore.instance();
  }

  public static AuthenticationManager instance() {
    if(instance == null)
      instance = new AuthenticationManager();
    return instance;
  }

  public void login(String sessionId, String sessionKey) throws InvalidCredentialsException, NetworkConnectionException {
    // Make request to the server for an access token
    // If 500 returned, throw an InvalidCredentialsException
    // Otherwise, store the returned token in the NetworkingStore
    HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
    final Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://localhost:3000/session/auth/login").content("session_id=1&session_key").build();
    try {
      Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
          System.out.println(httpRequest.getUrl());
          switch(httpResponse.getStatus().getStatusCode()) {
            case 404:
              throw new RuntimeException(new NetworkConnectionException());
            case 500:
              throw new RuntimeException(new InvalidCredentialsException());
            case 200:
              // Extract token from json response
              String jsonResponseStringRaw = httpResponse.getResultAsString().replace("\\", "");
              String jsonResponseString = jsonResponseStringRaw.substring(1, jsonResponseStringRaw.length() - 1);
              JSONObject jsonResponse = (JSONObject) JSONValue.parse(jsonResponseString);

              String tokenString = (String) jsonResponse.get("token");
              NetworkingToken token = new NetworkingToken(tokenString);
              networkingStore.setToken(token);

              return;
          }
        }

        @Override
        public void failed(Throwable t) {
          throw new RuntimeException(t);
        }

        @Override
        public void cancelled() {}
      });
    } catch(RuntimeException e) {
      System.out.println("---> " + e.getCause());
    }
  }
}
