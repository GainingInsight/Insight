package com.insight;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.insight.settings.Settings;


public class Assets {
  // Main texture files
  public static Texture loginTextures;

  // Screen backgrounds
  public static Texture loginBackground;
  public static Texture settingsBackground;
  public static Texture spriteSheet;

  public static TextureRegion loginBackgroundRegion;
  public static TextureRegion settingsBackgroundRegion;

  public static Sprite loginButton;

  public static Animation playerWalking;

  public static Texture loadTexture(String file) {
    return new Texture(Gdx.files.internal(file));
  }

  public static void load (){
    loginBackground = loadTexture("badlogic.jpg");
    loginBackgroundRegion = new TextureRegion(loginBackground, 0, 0, 200, 200);

    loginTextures = loadTexture("logintextures.png");
    loginButton = new Sprite(new TextureRegion(loginTextures, 0, 0, 295, 61));


    spriteSheet = loadTexture("spriteSheet.png");
    // TEST: 60x60 = size of sprite, 120 = both right walking sprites
    playerWalking = new Animation(0.2f, new TextureRegion(spriteSheet, 0, 120, 60, 60), new TextureRegion(spriteSheet, 60, 120, 60, 60));

  }

  public static void playSound(Sound sound) {
    if(Settings.soundEnabled) sound.play(1);
  }
}
