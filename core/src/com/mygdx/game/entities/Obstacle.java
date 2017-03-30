package com.mygdx.game.entities;


import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.configurations.GameConfig;

public class Obstacle extends GameObjectBase implements Pool.Poolable {

    private static final Logger log = new Logger(Obstacle.class.getName(), Logger.DEBUG);
    private float ySpeed = GameConfig.OBSTACLE_MEDIUM_SPEED;  //obstacles falling speed //no need to reset cause CONSTANT
    private boolean overlapped; //default is false
    private boolean collected; //default is false

    private float timer, seconds, speed;
    private float max_timer = 1;
    private float default_speed = GameConfig.OBSTACLE_DEFAULT_SPEED;

    public Obstacle() {
        //calls constructor from super class
        super(GameConfig.OBSTACLE_BOUNDS_RADIUS);
        setSize(GameConfig.OBSTACLE_BOUNDS_DIMENSION, GameConfig.OBSTACLE_BOUNDS_DIMENSION);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }


    public void update() {
        //obstacles moving downward
             setY(getY() - ySpeed);

        //Calculate Obstacles' speed over time
//        float delta = Gdx.graphics.getDeltaTime();
//        timer += delta;
//        if(timer >= max_timer){
//            seconds += 1;
//            speed = default_speed + (seconds / 1000);
//            timer = 0;
//        }
//
//        if(speed >= 0.30f){
//            speed = 0.30f;
//        }
//        setY(getY() - speed);

        //setPosition(getX(), getY() - ySpeed);
    }


    public boolean isPlayerColliding(Player player) {
        //crating player bounds from GameObjectBase
        Circle playerBounds = player.getBounds();
        //check for overlap
        overlapped = Intersector.overlaps(playerBounds, getBounds());
        return overlapped;
    }

    public boolean getOverlapped() {
        return overlapped;
    }

    public boolean getCollected() {
        return collected;
    }

    public void setCollected() {
        collected = true;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public void reset() {
        overlapped = false;
        collected = false;
    }

}
