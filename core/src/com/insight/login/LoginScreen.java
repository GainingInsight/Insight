package com.insight.login;

import com.insight.InsightGame;
import com.insight.Assets;
import com.insight.settings.Settings;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.math.*;

import com.badlogic.gdx.graphics.*;

public class LoginScreen extends ScreenAdapter {
  InsightGame game;
  OrthographicCamera guiCamera;


  Vector3 touchVector;

  Rectangle testBounds;

  public LoginScreen(InsightGame game) {
    this.game = game;

    guiCamera = new OrthographicCamera(800, 600);
    guiCamera.position.set(800 / 2, 600 / 2, 0);

    touchVector = new Vector3();

    testBounds = new Rectangle(0, 0, 300, 300);
  }

  private void update() {
    if(Gdx.input.justTouched()) {
      guiCamera.unproject(touchVector.set(Gdx.input.getX(), Gdx.input.getY(), 0));

      if(testBounds.contains(touchVector.x, touchVector.y)) {
        System.out.println("TOUCHED INSIDE TEST BOUNDS");
        return;
      }
    }

  }

  private void render() {
    GL20 gl = Gdx.gl;

    gl.glClearColor(0.155f, 0.261f, 0.272f, 1);

    gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    guiCamera.update();
    game.batch.setProjectionMatrix(guiCamera.combined);


    // Render background image

    game.batch.disableBlending();
    game.batch.begin();
    game.batch.draw(Assets.loginBackground, 0, 0, 320, 480);
    game.batch.end();


    // Render all other items
    game.batch.enableBlending();
    game.batch.begin();
    game.batch.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274, 142);

    game.batch.end();
  }

  @Override
  public void render(float delta) {
    update();
    render();
  }
}
