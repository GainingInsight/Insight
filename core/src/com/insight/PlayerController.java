package com.insight;

import com.insight.game.objects.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;
//import java.awt.Rectangle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
//import com.sun.xml.internal.xsom.impl.scd.Iterators.Array;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by Catherine on 9/6/15.
 */

//TODO: refactor for multiplayer -- call twice from PlayerController w/ two diff inputs? Handle here by moving the correct player given xyz?

public class PlayerController {
    float spriteVelocityY = 0;
    float spriteVelocityX = 0;
    Vector2 spriteVelocity = new Vector2(spriteVelocityX,spriteVelocityY);
    TiledMap map;
    Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };
    private Array<Rectangle> tiles = new Array<Rectangle>();


    //TODO: have movement speed use deltaTime instead depending on render speed
    public void update(Sprite player, TiledMap map, float deltaTime){
        if(deltaTime==0) return;

        this.map = map;
        float height = player.getHeight();
        float width = player.getWidth();
        boolean moving = false;


        //
        // movement
        //

        // move left
        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            //player.isMoving(true);
            //player.setDirection(false);
            spriteVelocityX = -10f;
            //player.translate(-10.0f,0);
            moving = true;
        }

        // move right
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            //player.isMoving(true);
            //player.setDirection(true);
            spriteVelocityX = 10f;
            //player.translate(10.0f,0);
            moving = true;
        }

        // move up
        if (Gdx.input.isKeyPressed(Keys.UP)){
            //player.isMoving(true);
            //player.setDirection(true);
            spriteVelocityY = 10f;
            //player.translate(0,10f);
            moving = true;
        }

        // move down
        if (Gdx.input.isKeyPressed(Keys.DOWN)){
            //player.isMoving(true);
            //player.setDirection(true);
            spriteVelocityY = -10f;
            //player.translate(0,-10f);
            moving = true;
        }

        // sprite standing still
        if (Math.abs(spriteVelocityX) < 1) {
            spriteVelocityX = 0;
            moving = false;
        }
        // upper bound on x-axis velocity
        if (Math.abs(spriteVelocityX) > 10f) {
            spriteVelocityX = Math.signum(spriteVelocityX) * 10f;
        }

        //
        // x-axis collision detection
        //
        Rectangle spriteBox = rectPool.obtain();
        spriteBox.set(player.getX(), player.getY(), width, height);
        int startX, startY, endX, endY;
        if (spriteVelocityX > 0) {
            System.out.print("vel >0");
            startX = endX = (int)(player.getX() + width + spriteVelocityX);
        } else {
            startX = endX = (int)(player.getX() + spriteVelocityX);
        }
        startY = (int)(player.getY());
        endY = (int)(player.getY() + height);
        getTiles(startX, startY, endX, endY, tiles);
        spriteBox.x += spriteVelocityX;
        for (Rectangle tile : tiles) {
            if (spriteBox.overlaps(tile)) {
                System.out.print("true");
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
        for (Rectangle tile : tiles) {
            if (spriteBox.overlaps(tile)) {
                // we actually reset the player y-position here
                // so it is just below/above the tile we collided with
                // this removes bouncing
                if (spriteVelocityY > 0) {
                    player.setY(tile.y - height);

                } else {
                    player.setY(tile.y + tile.height);
                }
                spriteVelocityY = 0;
                break;
            }
        }

        rectPool.free(spriteBox);

        // unscale the velocity by the inverse delta time and set
        // the latest position
        player.translate(spriteVelocityX, spriteVelocityY);
        //velocity.scl(1 / deltaTime);

        //NOTE: setting player position here to 0,0 triggers the overlap function, unsure why
        //player.setPosition(player.getX()+spriteVelocityX,player.getY()+spriteVelocityY);


        // Apply damping to the velocity on the x-axis and y-axis so we don't
        // walk infinitely once a key was pressed
        spriteVelocityX *= 0.87f;
        spriteVelocityY *= 0.87f;


    }
    private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {

        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Walls");
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 10, 10);
                    tiles.add(rect);
                    //TEST
                    System.out.print("added tile");
                }
            }
        }
    }


}
