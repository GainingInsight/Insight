package com.insight;

import com.insight.game.objects.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Sprite;


/**
 * Created by Catherine on 9/6/15.
 */

//TODO: refactor for multiplayer -- call twice from PlayerController w/ two diff inputs? Handle here by moving the correct player given xyz?

public class PlayerController {

    //TODO: have movement speed use deltaTime instead depending on render speed
    public static void update(Sprite player, float deltaTime){

        // move left
        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            //player.isMoving(true);
            //player.setDirection(false);
            player.translate(-10.0f,0);
        }

        // move right
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            //player.isMoving(true);
            //player.setDirection(true);
            player.translate(10.0f,0);
        }

        // move up
        if (Gdx.input.isKeyPressed(Keys.UP)){
            //player.isMoving(true);
            //player.setDirection(true);
            player.translate(0,10.f);
        }

        // move down
        if (Gdx.input.isKeyPressed(Keys.DOWN)){
            //player.isMoving(true);
            //player.setDirection(true);
            player.translate(0,-10.f);
        }



    }
}
