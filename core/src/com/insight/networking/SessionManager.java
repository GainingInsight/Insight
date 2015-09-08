package com.insight.networking;

/**
 * Created by jamesyanyuk on 9/6/15.
 */
public class SessionManager {
  private static SessionManager instance = null;

  private SessionManager() {}

  public static SessionManager instance() {
    if(instance == null)
      instance = new SessionManager();
    return instance;
  }
}
