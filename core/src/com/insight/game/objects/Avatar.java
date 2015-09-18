package com.insight.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;


/**
 * Created by Catherine on 9/6/15.
 */
public class Avatar {
    private float MAX_VELOCITY = 10f;
    //private float JUMP_VELOCITY = 40f;
    private float DAMPING = 0.87f;
    private Sprite playerSprite;
    private boolean moving;

    public static Vector2 START_POSITION = new Vector2(50, 220);

    enum State {
        Standing, Walking
    }

    final Vector2 position = new Vector2();
    final Vector2 velocity = new Vector2();

    State state = State.Walking;
    float stateTime = 0;
    boolean facesLeft = true;

    public boolean keyLeftPressed = false;
    public boolean keyRightPressed = false;
    public boolean keyUpPressed = false;
    public boolean keyDownPressed = false;

    public Avatar() {
        position.x = 50;
        position.y = 530;
    }

    public void setPlayerSprite(Sprite sprite) {
      playerSprite = sprite;
    }

    public Sprite getPlayerSprite() {
      return playerSprite;
    }

    public void translate() {
      playerSprite.translate(velocity.x, velocity.y);
    }

    public void setPosition(float xPos, float yPos){
      System.out.println("B: " + this);
      playerSprite.setPosition(xPos, yPos);
    }

    public void setStateTime(float time){
        stateTime = time;
    }

    public void setVelocity(float newVelocity){
        velocity.x = newVelocity;
    }

    public void setMoving(boolean moving){
        if(moving) state = State.Walking;
        else state = State.Standing;

        this.moving = moving;
    }

    public boolean getMoving() {
      return moving;
    }

    public void setDirection(boolean left){
        if(left) facesLeft = true;
        else facesLeft = false;
    }

    public float getWidth(){
        return playerSprite.getWidth();
    }

    public float getHeight(){
        return playerSprite.getHeight();
    }

    public float getX(){
        return playerSprite.getX();
    }

    public float getY(){
      return playerSprite.getY();
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
        return facesLeft;
    }
}
