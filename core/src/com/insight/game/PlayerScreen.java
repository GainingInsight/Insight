package com.insight.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.insight.InsightGame;
import com.insight.Assets;
import com.insight.settings.Settings;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.math.*;

import com.badlogic.gdx.graphics.*;

public class PlayerScreen extends ScreenAdapter implements InputProcessor {
  InsightGame game;
  OrthographicCamera guiCamera;

  Sprite loginButton;
  BoundingBox collisionLoginButton = new BoundingBox();

  Ray collisionRay;

  SpriteBatch loginBatch;

  public PlayerScreen(InsightGame game) {
    this.game = game;
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public void show() {
    loginButton = Assets.loginButton;
    loginButton.setPosition(50, 50);
    loginButton.setColor(1,1,1,0.5f);
    collisionLoginButton.set(new Vector3(loginButton.getVertices()[0], loginButton.getVertices()[1], -10),new Vector3(loginButton.getVertices()[10], loginButton.getVertices()[11], 10));

    loginBatch = new SpriteBatch();
    loginBatch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 600);

    guiCamera = new OrthographicCamera(800, 600);
    guiCamera.position.x = 400;
    guiCamera.position.y = 300;
    guiCamera.update();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    loginBatch.begin();
    loginButton.draw(loginBatch);
    loginBatch.end();
  }

  @Override
  public boolean keyDown(int keycode) {
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    collisionRay = guiCamera.getPickRay(screenX, screenY);

    if (Intersector.intersectRayBoundsFast(collisionRay, collisionLoginButton)) {
      System.out.println("HEY");
    }

    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    return false;
  }
}
