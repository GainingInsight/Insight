package com.insight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.insight.game.objects.Avatar;
import com.insight.networking.Message;
import com.insight.networking.MovementMessage;
import com.insight.networking.SessionManager;

import java.util.HashMap;

public class PlayerController {
    private TiledMap map;
    private Pool<Rectangle> rectPool;
    private Array<Rectangle> tiles;
    private SessionManager session;
    private Avatar currentPlayer;
    private Avatar otherPlayer;

    public PlayerController(Stage stage) {
      session = SessionManager.instance();

      if(session.clientRole.equals("playerNS")) {
        currentPlayer = session.getPlayers().get("playerNS");
        otherPlayer = session.getPlayers().get("playerFS");
      } else {
        currentPlayer = session.getPlayers().get("playerFS");
        otherPlayer = session.getPlayers().get("playerNS");
      }

      rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
          return new Rectangle();
        }
      };

      tiles = new Array<Rectangle>();

      stage.addListener(new InputListener() {
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
          switch(keycode) {
            case Keys.LEFT:
              session.send(new MovementMessage(Message.KEY_LEFT, true, currentPlayer.getX(), currentPlayer.getY()));
              break;
            case Keys.RIGHT:
              session.send(new MovementMessage(Message.KEY_RIGHT, true, currentPlayer.getX(), currentPlayer.getY()));
              break;
            case Keys.UP:
              session.send(new MovementMessage(Message.KEY_UP, true, currentPlayer.getX(), currentPlayer.getY()));
              break;
            case Keys.DOWN:
              session.send(new MovementMessage(Message.KEY_DOWN, true, currentPlayer.getX(), currentPlayer.getY()));
              break;
          }
          return false;
        }

        @Override
        public boolean keyUp(InputEvent event, int keycode) {
          switch(keycode) {
            case Keys.LEFT:
              session.send(new MovementMessage(Message.KEY_LEFT, false, currentPlayer.getX(), currentPlayer.getY()));
              break;
            case Keys.RIGHT:
              session.send(new MovementMessage(Message.KEY_RIGHT, false, currentPlayer.getX(), currentPlayer.getY()));
              break;
            case Keys.UP:
              session.send(new MovementMessage(Message.KEY_UP, false, currentPlayer.getX(), currentPlayer.getY()));
              break;
            case Keys.DOWN:
              session.send(new MovementMessage(Message.KEY_DOWN, false, currentPlayer.getX(), currentPlayer.getY()));
              break;
          }
          return false;
        }
      });
    }

    public void update(TiledMap map, float deltaTime){
        if(deltaTime == 0) return;

        currentPlayer.setDirection(true);
        otherPlayer.setDirection(true);

        currentPlayer.setMoving(false);
        otherPlayer.setMoving(false);

        this.map = map;

        //
        // movement
        //
        // move left
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            currentPlayer.getVelocity().x = -4f;
            currentPlayer.setDirection(true);
            currentPlayer.setMoving(true);
        }
        if(otherPlayer.keyLeftPressed) {
            otherPlayer.getVelocity().x = -4f;
            otherPlayer.setDirection(true);
            otherPlayer.setMoving(true);
        }

        // move right
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            currentPlayer.getVelocity().x = 4f;
            currentPlayer.setDirection(false);
            currentPlayer.setMoving(true);
        }
        if(otherPlayer.keyRightPressed){
            otherPlayer.getVelocity().x = 4f;
            otherPlayer.setDirection(false);
            otherPlayer.setMoving(true);
        }

        // move up
        if(Gdx.input.isKeyPressed(Keys.UP)){
            currentPlayer.getVelocity().y = 4f;
            currentPlayer.setMoving(true);
        }
        if(otherPlayer.keyUpPressed){
            otherPlayer.getVelocity().y = 4f;
            otherPlayer.setMoving(true);
        }

        // move down
        if(Gdx.input.isKeyPressed(Keys.DOWN)){
            currentPlayer.getVelocity().y = -4f;
            currentPlayer.setMoving(true);
        }
        if(otherPlayer.keyDownPressed){
            otherPlayer.getVelocity().y = -4f;
            otherPlayer.setMoving(true);
        }

        // Face players left or right
        currentPlayer.getPlayerSprite().setFlip(currentPlayer.getDirection(), false);
        otherPlayer.getPlayerSprite().setFlip(otherPlayer.getDirection(), false);

        // current player standing still
        if (Math.abs(currentPlayer.getVelocity().x) < 1) {
            currentPlayer.getVelocity().x = 0;
            currentPlayer.setMoving(false);
        }

        // other player standing still
        if (Math.abs(otherPlayer.getVelocity().x) < 1) {
          otherPlayer.getVelocity().x = 0;
          otherPlayer.setMoving(false);
        }

        // upper bound on x-axis velocity for current player
        if (Math.abs(currentPlayer.getVelocity().x) > 4f) {
            currentPlayer.getVelocity().x = Math.signum(currentPlayer.getVelocity().x) * 4f;
        }

        // upper bound on x-axis velocity for other player
        if (Math.abs(otherPlayer.getVelocity().x) > 4f) {
          otherPlayer.getVelocity().x = Math.signum(otherPlayer.getVelocity().x) * 4f;
        }

        //
        // x-axis collision detection for current player
        //
        Rectangle spriteBoxCurrent = rectPool.obtain();
        spriteBoxCurrent.set(currentPlayer.getX(), currentPlayer.getY(), currentPlayer.getWidth(), currentPlayer.getHeight());
        int startXCurrent, startYCurrent, endXCurrent, endYCurrent;
        // Moving right
        if (currentPlayer.getVelocity().x > 0) {
            startXCurrent = endXCurrent = (int)(currentPlayer.getX() + currentPlayer.getWidth() + currentPlayer.getVelocity().x);
        }
        // Moving left
        else {
            startXCurrent = endXCurrent = (int)(currentPlayer.getX() + currentPlayer.getVelocity().x);
        }
        startYCurrent = (int)(currentPlayer.getY());
        endYCurrent = (int)(currentPlayer.getY() + currentPlayer.getHeight());
        getTiles(startXCurrent, startYCurrent, endXCurrent, endYCurrent, tiles);
        spriteBoxCurrent.x += currentPlayer.getVelocity().x;
        // Check collision with wall tile
        for (Rectangle tile : tiles) {
            if (spriteBoxCurrent.overlaps(tile)) {
              currentPlayer.getVelocity().x = 0;
              break;
            }
        }
        spriteBoxCurrent.x = currentPlayer.getX();

        //
        // x-axis collision detection for other player
        //
        Rectangle spriteBoxOther = rectPool.obtain();
        spriteBoxOther.set(otherPlayer.getX(), otherPlayer.getY(), otherPlayer.getWidth(), otherPlayer.getHeight());
        int startXOther, startYOther, endXOther, endYOther;
        // Moving right
        if (otherPlayer.getVelocity().x > 0) {
          startXOther = endXOther = (int)(otherPlayer.getX() + otherPlayer.getWidth() + otherPlayer.getVelocity().x);
        }
        // Moving left
        else {
          startXOther = endXOther = (int)(otherPlayer.getX() + otherPlayer.getVelocity().x);
        }
        startYOther = (int)(otherPlayer.getY());
        endYOther = (int)(otherPlayer.getY() + otherPlayer.getHeight());
        getTiles(startXOther, startYOther, endXOther, endYOther, tiles);
        spriteBoxOther.x += otherPlayer.getVelocity().x;
        // Check collision with wall tile
        for (Rectangle tile : tiles) {
          if (spriteBoxOther.overlaps(tile)) {
            otherPlayer.getVelocity().x = 0;
            break;
          }
        }
        spriteBoxOther.x = otherPlayer.getX();

        //
        // y-axis collision detection for current player
        //
        // moving up: check the tiles to the top of player's
        // top bounding box edge
        // moving down: check the tiles to the bottom

        if (currentPlayer.getVelocity().y > 0) {
          startYCurrent = endYCurrent = (int) (currentPlayer.getY() + currentPlayer.getHeight() + currentPlayer.getVelocity().y);
        } else {
          startYCurrent = endYCurrent = (int)(currentPlayer.getY() + currentPlayer.getVelocity().y);
        }
        startXCurrent = (int)(currentPlayer.getX());
        endXCurrent = (int)(currentPlayer.getX() + currentPlayer.getWidth());
        getTiles(startXCurrent, startYCurrent, endXCurrent, endYCurrent, tiles);
        spriteBoxCurrent.y += currentPlayer.getVelocity().y;
        // Check collision with wall tile
        for (Rectangle tile : tiles) {
          if (spriteBoxCurrent.overlaps(tile)) {
            currentPlayer.getVelocity().y = 0;
            break;
          }
        }

        //
        // y-axis collision detection for other player
        //
        // moving up: check the tiles to the top of player's
        // top bounding box edge
        // moving down: check the tiles to the bottom

        if (otherPlayer.getVelocity().y > 0) {
          startYOther = endYOther = (int) (otherPlayer.getY() + otherPlayer.getHeight() + otherPlayer.getVelocity().y);
        } else {
          startYOther = endYOther = (int)(otherPlayer.getY() + otherPlayer.getVelocity().y);
        }
        startXOther = (int)(otherPlayer.getX());
        endXOther = (int)(otherPlayer.getX() + otherPlayer.getWidth());
        getTiles(startXOther, startYOther, endXOther, endYOther, tiles);
        spriteBoxOther.y += otherPlayer.getVelocity().y;
        // Check collision with wall tile
        for (Rectangle tile : tiles) {
          if (spriteBoxOther.overlaps(tile)) {
            otherPlayer.getVelocity().y = 0;
            break;
          }
        }

        rectPool.free(spriteBoxOther);

        // unscale the velocity by the inverse delta time and set
        // the latest position
        currentPlayer.translate();
        otherPlayer.translate();
        //velocity.scl(1 / deltaTime);

        // Apply damping to the velocity on the x-axis and y-axis so we don't
        // walk infinitely once a key was pressed
        currentPlayer.getVelocity().x *= 0.67f;
        currentPlayer.getVelocity().y *= 0.67f;

        otherPlayer.getVelocity().x *= 0.67f;
        otherPlayer.getVelocity().y *= 0.67f;
    }
    private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Walls");

        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                Cell cell = layer.getCell((int)x/10, (int)y/10);

                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 10, 10);
                    tiles.add(rect);
                }
            }
        }
    }
}
