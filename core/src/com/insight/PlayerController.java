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

//TODO: refactor for multiplayer -- call twice from PlayerController w/ two diff inputs? Handle here by moving the correct player given xyz?

public class PlayerController {

    private float spriteVelocityY = 0;
    private float spriteVelocityX = 0;
    private Vector2 spriteVelocity = new Vector2(spriteVelocityX,spriteVelocityY);
    private TiledMap map;
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };
    private Array<Rectangle> tiles = new Array<Rectangle>();


    public void update(Sprite player, TiledMap map, float deltaTime){
        if(deltaTime == 0) return;

        this.map = map;
        float height = player.getHeight();
        float width = player.getWidth();
        boolean facesLeft = true;
        boolean moving = false;


        //
        // movement
        //
        // move left
        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            spriteVelocityX = -4f;
            facesLeft = true;
            moving = true;
        }

        // move right
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            spriteVelocityX = 4f;
            facesLeft = false;
            moving = true;
        }

        // move up
        if (Gdx.input.isKeyPressed(Keys.UP)){
            spriteVelocityY = 4f;
            moving = true;
        }

        // move down
        if (Gdx.input.isKeyPressed(Keys.DOWN)){
            spriteVelocityY = -4f;
            moving = true;
        }
        // Face player left or right
        if(moving) player.setFlip(facesLeft,false);

        // player standing still
        if (Math.abs(spriteVelocityX) < 1) {
            spriteVelocityX = 0;
            moving = false;
        }
        // upper bound on x-axis velocity
        if (Math.abs(spriteVelocityX) > 4f) {
            spriteVelocityX = Math.signum(spriteVelocityX) * 4f;
        }

        //
        // x-axis collision detection
        //
        Rectangle spriteBox = rectPool.obtain();
        spriteBox.set(player.getX(), player.getY(), width, height);
        int startX, startY, endX, endY;
        // Moving right
        if (spriteVelocityX > 0) {
            startX = endX = (int)(player.getX() + width + spriteVelocityX);
        }
        // Moving left
        else {
            startX = endX = (int)(player.getX() + spriteVelocityX);
        }
        startY = (int)(player.getY());
        endY = (int)(player.getY() + height);
        getTiles(startX, startY, endX, endY, tiles);
        spriteBox.x += spriteVelocityX;
        // Check collision with wall tile
        for (Rectangle tile : tiles) {
            if (spriteBox.overlaps(tile)) {
                spriteVelocityX = 0;
                break;
            }
        }
        spriteBox.x = player.getX();

        //
        // y-axis collision detection
        //
        // moving up: check the tiles to the top of player's
        // top bounding box edge
        // moving down: check the tiles to the bottom

        if (spriteVelocityY > 0) {
            startY = endY = (int) (player.getY() + height + spriteVelocityY);
        } else {
            startY = endY = (int)(player.getY() + spriteVelocityY);
        }
        startX = (int)(player.getX());
        endX = (int)(player.getX() + width);
        getTiles(startX, startY, endX, endY, tiles);
        spriteBox.y += spriteVelocityY;
        // Check collision with wall tile
        for (Rectangle tile : tiles) {
            if (spriteBox.overlaps(tile)) {
                spriteVelocityY = 0;
                break;
            }
        }


        rectPool.free(spriteBox);

        // unscale the velocity by the inverse delta time and set
        // the latest position
        player.translate(spriteVelocityX, spriteVelocityY);
        //velocity.scl(1 / deltaTime);

        // Apply damping to the velocity on the x-axis and y-axis so we don't
        // walk infinitely once a key was pressed
        spriteVelocityX *= 0.67f;
        spriteVelocityY *= 0.67f;
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
