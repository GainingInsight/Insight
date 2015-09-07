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


    public Avatar(Texture texture) {
        playerTexture = texture;
        position.x = 50;
        position.y = 100;

    }
    public void movePosition(Vector2 newVelocity, float deltaTime){
        position.add(newVelocity);

        velocity.scl(1 / deltaTime);
        velocity.x *= DAMPING;
    }

    public void setPosition(float xPos, float yPos){
        position.set(xPos, yPos);

    }


    public void setStateTime(float time){
        stateTime = time;
    }

    public void setVelocity(float newVelocity){
        velocity.x = newVelocity;
    }

    public void isMoving(boolean moving){
        if(moving) state = State.Walking;
        else state = State.Standing;
    }

    public void setDirection(boolean right){
        if(right) facesRight = true;
        else facesRight = false;
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

    public Vector2 getVelocity(){
        return velocity;

    }
    public State getState(){
        return state;
    }

    public float getStateTime(){
        return stateTime;
    }

    public boolean getDirection(){
        return facesRight;
    }

    public Texture getTexture(){
        return playerTexture;
    }
}
