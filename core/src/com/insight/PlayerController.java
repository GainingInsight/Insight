package com.insight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.insight.game.objects.Avatar;
import com.insight.networking.SessionManager;

import java.util.HashMap;

//TODO: refactor for multiplayer -- call twice from PlayerController w/ two diff inputs? Handle here by moving the correct player given xyz?

public class PlayerController {
    private TiledMap map;
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };
    private Array<Rectangle> tiles = new Array<Rectangle>();
    private SessionManager session = SessionManager.instance();


    public void update(HashMap<String, Avatar> playerGroup, TiledMap map, float deltaTime){
        if(deltaTime == 0) return;

        Avatar currentPlayer = session.getPlayers().get("currentPlayer");
        Avatar otherPlayer = session.getPlayers().get("otherPlayer");

        currentPlayer.setDirection(true);
        otherPlayer.setDirection(true);

        currentPlayer.setMoving(false);
        otherPlayer.setMoving(false);

        this.map = map;


        //
        // movement
        //
        // move left
        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            currentPlayer.getVelocity().x = -4f;
            currentPlayer.setDirection(true);
            currentPlayer.setMoving(true);
        }

        // move right
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            currentPlayer.getVelocity().x = 4f;
            currentPlayer.setDirection(false);
            currentPlayer.setMoving(true);
        }

        // move up
        if (Gdx.input.isKeyPressed(Keys.UP)){
            currentPlayer.getVelocity().y = 4f;
            currentPlayer.setMoving(true);
        }

        // move down
        if (Gdx.input.isKeyPressed(Keys.DOWN)){
            currentPlayer.getVelocity().y = -4f;
            currentPlayer.setMoving(true);
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

        // upper bound on x-axis velocity for current player
        if (Math.abs(otherPlayer.getVelocity().x) > 4f) {
          otherPlayer.getVelocity().x = Math.signum(otherPlayer.getVelocity().x) * 4f;
        }

        //
        // x-axis collision detection
        //
        Rectangle spriteBox = rectPool.obtain();
        spriteBox.set(currentPlayer.getX(), currentPlayer.getY(), currentPlayer.getWidth(), currentPlayer.getHeight());
        int startX, startY, endX, endY;
        // Moving right
        if (currentPlayer.getVelocity().x > 0) {
            startX = endX = (int)(currentPlayer.getX() + currentPlayer.getWidth() + currentPlayer.getVelocity().x);
        }
        // Moving left
        else {
            startX = endX = (int)(currentPlayer.getX() + currentPlayer.getVelocity().x);
        }

        startY = (int)(currentPlayer.getY());
        endY = (int)(currentPlayer.getY() + currentPlayer.getHeight());
        getTiles(startX, startY, endX, endY, tiles);
        spriteBox.x += currentPlayer.getVelocity().x;

        // Check collision with wall tile
        for (Rectangle tile : tiles) {
            if (spriteBox.overlaps(tile)) {
                currentPlayer.getVelocity().x = 0;
                break;
            }
        }
        spriteBox.x = currentPlayer.getX();

        //
        // y-axis collision detection
        //
        // moving up: check the tiles to the top of player's
        // top bounding box edge
        // moving down: check the tiles to the bottom

        if (currentPlayer.getVelocity().y > 0) {
            startY = endY = (int) (currentPlayer.getY() + currentPlayer.getHeight() + currentPlayer.getVelocity().y);
        } else {
            startY = endY = (int)(currentPlayer.getY() + currentPlayer.getVelocity().y);
        }

        startX = (int)(currentPlayer.getY());
        endX = (int)(currentPlayer.getY() + currentPlayer.getWidth());
        getTiles(startX, startY, endX, endY, tiles);
        spriteBox.y += currentPlayer.getVelocity().y;

        // Check collision with wall tile
        for (Rectangle tile : tiles) {
            if (spriteBox.overlaps(tile)) {
                currentPlayer.getVelocity().y = 0;
                break;
            }
        }

        rectPool.free(spriteBox);

        // unscale the velocity by the inverse delta time and set
        // the latest position
        currentPlayer.translate();
        otherPlayer.translate();
        //velocity.scl(1 / deltaTime);

        // Apply damping to the velocity on the x-axis and y-axis so we don't
        // walk infinitely once a key was presseded
        currentPlayer.getVelocity().x *= 0.67f;
        currentPlayer.getVelocity().y *= 0.67f;
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
