package com.insight.login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.insight.InsightGame;
import com.insight.game.PlayerScreen;

public class LoginScreen extends ScreenAdapter {
  Skin skin;
  Stage stage;
  Texture loginTextures;
  ImageButton loginButton;
  Image loginLogo;
  Label sessionIdLabel;
  Label sessionKeyLabel;
  TextField sessionIdField;
  TextField sessionKeyField;

  InsightGame game;

  public LoginScreen(InsightGame game) {
    this.game = game;
  }

  @Override
  public void show() {
    skin = new Skin(Gdx.files.internal("uiskin.json"));
    loginTextures = new Texture(Gdx.files.internal("logintextures.png"));
    TextureRegion loginButtonTextureUp = new TextureRegion(loginTextures, 0, 0, 295, 42);
    TextureRegion loginButtonTextureDown = new TextureRegion(loginTextures, 0, 42, 295, 42);
    TextureRegion loginLogoTexture = new TextureRegion(loginTextures, 0, 84, 295, 110);

    stage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(stage);

    ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
    style.imageUp = new TextureRegionDrawable(loginButtonTextureUp);
    style.imageDown = new TextureRegionDrawable(loginButtonTextureDown);

    loginLogo = new Image(loginLogoTexture);
    loginButton = new ImageButton(style);

    sessionIdLabel = new Label("Session ID:", skin);
    sessionKeyLabel = new Label("Session Key:", skin);

    sessionIdField = new TextField("", skin);
    sessionIdField.setMessageText("Session ID");

    sessionKeyField = new TextField("", skin);
    sessionKeyField.setMessageText("Session Key");

    Table t = new Table();

    t.add(loginLogo).width(295).height(110);
    t.row();
    t.add(sessionIdLabel).width(295);
    t.row();
    t.add(sessionIdField).width(295);
    t.row();
    t.add(sessionKeyLabel).width(295);
    t.row();
    t.add(sessionKeyField).width(295).padBottom(10);
    t.row();
    t.add(loginButton);

    t.layout();

    stage.addActor(t);

    t.setPosition(400, 300);

    sessionIdField.setTextFieldListener(new TextFieldListener() {
      public void keyTyped(TextField textField, char key) {
        if (key == '\n') textField.getOnscreenKeyboard().show(false);
      }
    });

    sessionKeyField.setTextFieldListener(new TextFieldListener() {
      public void keyTyped(TextField textField, char key) {
        if (key == '\n') textField.getOnscreenKeyboard().show(false);
      }
    });

    loginButton.addListener(new ChangeListener() {
      public void changed (ChangeEvent event, Actor actor) {
        game.setScreen(new PlayerScreen(game));
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
    loginTextures.dispose();
  }
}
