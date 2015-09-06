package com.insight.login;

import com.insight.InsightGame;
import com.insight.Assets;
import com.insight.settings.Settings;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;

public class LoginScreen extends ScreenAdapter {
  InsightGame game;
  OrthographicCamera guiCamera;

  public LoginScreen(InsightGame game) {
    this.game = game;
  }

  private void update() {

  }

  private void render() {
    GL20 gl = Gdx.gl;
    gl.glClearColor(1, 0, 0, 1);
    gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    guiCamera.update();
    game.batch.setProjectionMatrix(guiCamera.combined);

    game.batch.disableBlending();
    game.batch.begin();
    game.batch.draw(Assets.loginBackground, 0, 0, 320, 480);
    game.batch.end();

    game.batch.enableBlending();
    game.batch.begin();
    game.batch.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274, 142);
    game.batch.draw(Assets.mainMenu, 10, 200 - 110 / 2, 300, 110);
    game.batch.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
    game.batch.end();
  }

  @Override
  public void render(float delta) {
    update();
    render();
  }
}
