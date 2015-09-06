package com.insight.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;


/**
 * Created by Catherine on 9/6/15.
 */
public class Avatar {
    private float WIDTH = 30;
    private float HEIGHT = 30;
    private float MAX_VELOCITY = 10f;
    //private float JUMP_VELOCITY = 40f;
    private float DAMPING = 0.87f;
    private Texture playerTexture;


    enum State {
        Standing, Walking
    }

    final Vector2 position = new Vector2();
    final Vector2 velocity = new Vector2();

    State state = State.Walking;
    float stateTime = 0;
    boolean facesRight = true;


    public Avatar() {
        playerTexture = new Texture("badlogic.jpg");

    }

    public void setPosition(float xPos, float yPos){
        position.set(xPos, yPos);

    }

    public float getWidth(){
        return WIDTH;

    }

    public float getHeight(){
        return HEIGHT;
    }
    public Vector2 getPosition(){
        return position;
    }

    public State getState(){
        return state;
    }

    public boolean getDirection(){
        return facesRight;
    }

    public Texture getTexture(){
        return playerTexture;
    }
}
