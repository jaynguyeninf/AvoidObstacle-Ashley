package com.mygdx.game.entities;

/**
 * Created by Vu on 2/9/2017.
 */

public class Background {

    private float x, y;
    private float width, height;

    public Background() {

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height){
        this.width = width;
        this.height = height;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }


}
