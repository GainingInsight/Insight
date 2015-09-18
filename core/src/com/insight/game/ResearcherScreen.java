package com.insight.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import com.insight.InsightGame;
import com.insight.game.PlayerScreen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.insight.networking.AuthenticationManager;
import com.insight.networking.InvalidCredentialsException;
import com.insight.networking.NetworkConnectionException;

import java.util.concurrent.Callable;

public class ResearcherScreen extends ScreenAdapter {
  Skin skin;
  Stage stage;
  Texture loginTextures;
  ImageButton loginButton;
  Image loginLogo;
  Label sessionIdLabel;
  Label sessionKeyLabel;
  Label notificationMessageLabel;
  TextField sessionIdField;
  TextField sessionKeyField;

  InsightGame game;

  AuthenticationManager authManager;

  public ResearcherScreen(InsightGame game) {
    this.game = game;
  }

  @Override
  public void show() {
    skin = new Skin(Gdx.files.internal("uiskin.json"));

    stage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(stage);

    stage.addListener(new InputListener() {
      @Override
      public boolean keyUp(InputEvent event, int keycode) {
        if(keycode == Input.Keys.ENTER) {
          System.out.println("HEY2");
        }
        return false;
      }
    });
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Additional drawing could be done here

    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
  }

  @Override
  public void resize (int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void dispose () {
    stage.dispose();
    skin.dispose();
  }
}
