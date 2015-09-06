package com.insight;

<<<<<<< HEAD
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Catherine on 9/5/15.
 */
public class Assets {
  // Main texture file
  public static Texture textures;
=======
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.insight.settings.Settings;

public class Assets {
  // Main texture files
  public static Texture loginTextures;
>>>>>>> 51212876360daff5ffaa5fa0b884d7307cf0ebb3

  // Screen backgrounds
  public static Texture loginBackground;
  public static Texture settingsBackground;
<<<<<<< HEAD

  public static void load (){
    textures
    loginBackground = new TextureRegion
=======
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
>>>>>>> 51212876360daff5ffaa5fa0b884d7307cf0ebb3
  }
}
