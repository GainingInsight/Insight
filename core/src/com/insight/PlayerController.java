package com.insight;

import com.insight.game.objects.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by Catherine on 9/6/15.
 */


public class PlayerController {


    public static void update(Avatar player, float deltaTime){

        if (deltaTime == 0) return;
        float playerStateTime = player.getStateTime();
         player.setStateTime(playerStateTime + deltaTime);

        // move left
        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            player.setVelocity(-10f);
            player.isMoving(true);
            player.setDirection(false);
        }

        // move right
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            player.setVelocity(10f);
            player.isMoving(true);
            player.setDirection(true);
        }
        Vector2 distanceTraveled = player.getVelocity();
        //Vector2 newVelocity = distanceTraveled.scl(deltaTime);

        //TODO: change to setPosition (change args)
        player.movePosition(distanceTraveled,deltaTime);
    }
}
