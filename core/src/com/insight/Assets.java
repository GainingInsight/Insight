package com.insight;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.insight.settings.Settings;

public class Assets {
  // Main texture files
  public static Texture loginTextures;

  // Screen backgrounds
  public static Texture loginBackground;
  public static Texture settingsBackground;

  public static TextureRegion loginBackgroundRegion;
  public static TextureRegion settingsBackgroundRegion;

  public static TextureRegion loginButton;

  public static Texture loadTexture(String file) {
    return new Texture(Gdx.files.internal(file));
  }

  public static void load (){
    loginBackground = loadTexture("badlogic.jpg");
    loginBackgroundRegion = new TextureRegion(loginBackground, 0, 0, 200, 200);

    loginTextures = loadTexture("logintextures.png");
    loginButton = new TextureRegion(loginTextures, 0, 0, 295, 61);
  }

  public static void playSound(Sound sound) {
    if(Settings.soundEnabled) sound.play(1);
  }
}
