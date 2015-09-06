package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class AuthenticationManager {
  private static AuthenticationManager instance = null;

  private AuthenticationManager() {}

  public static AuthenticationManager instance() {
    if(instance == null)
      instance = new AuthenticationManager();
    return instance;
  }

  public void login(String sessionId, String sessionKey) throws InvalidCredentialsException {
    // Make request to the server for an access token
    // If 500 returned, throw an InvalidCredentialsException
    // Otherwise, store the returned token in the NetworkingStore

    throw new InvalidCredentialsException("Test");
  }
}
