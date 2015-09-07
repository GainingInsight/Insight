package com.insight.login;

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

public class LoginScreen extends ScreenAdapter {
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

  public LoginScreen(InsightGame game) {
    this.game = game;
    authManager = AuthenticationManager.instance();
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

    notificationMessageLabel = new Label("", skin);
    notificationMessageLabel.setColor(Color.RED);

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
    t.add(loginButton).padBottom(10);
    t.row();
    t.add(notificationMessageLabel).align(Align.center);

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
        login();
      }
    });

    stage.addListener(new InputListener() {
      @Override
      public boolean keyUp(InputEvent event, int keycode) {
        if(keycode == Input.Keys.ENTER) {
          login();
        }
        return false;
      }
    });
  }

  public void switchScreen(final InsightGame game, final ScreenAdapter newScreen){
    stage.getRoot().getColor().a = 1;
    SequenceAction sequenceAction = new SequenceAction();
    sequenceAction.addAction(Actions.fadeOut(0.2f));
    sequenceAction.addAction(Actions.run(new Runnable() {
      @Override
      public void run() {
        game.setScreen(newScreen);
      }
    }));

    stage.getRoot().addAction(sequenceAction);
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

  private void login() {
    String sessionId = sessionIdField.getText();
    String sessionKey = sessionKeyField.getText();

    // Send request for login to server
    // Server will validate credentials, then create a token
    // Server will store token along with information about the user (sessionId and sessionKey)
    // Server will send back token to client, and client will store token
    //
    // When client has initiated a movement command,
    // Client will send movement message to server along with their token through a socket
    // Server will receive message from socket, and inspect the included token
    // Server will act on user:sessionKey associated to sessionId (both decoded from token)
    // Server will send broadcast to socket room associated to sessionId decoded from token

    // Log the user in; return exception if bad credentials, otherwise set token in store
    try {
      authManager.login(sessionId, sessionKey);
    } catch(InvalidCredentialsException e) {
      // Notify the user of an invalid session id/key combination
      notificationMessageLabel.setText("Invalid session id/key combination!");
      return;
    } catch(NetworkConnectionException e) {
      return;
    }

    switchScreen(game, new PlayerScreen(game));
  }
}
