package com.insight.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Catherine on 9/14/15.
 */
public class Player {
    private float velocityX;
    private float velocityY;
    private TextureRegion texture;
    private static Player instance = null;

    // Pass in sprite here?
    public static Player getInstance(){
        if(instance == null) instance = new Player();
        return instance;
    }
    private Player(){

    }
}
