package com.mygdx.game.configurations;

/**
 * Created by Vu on 1/26/2017.
 */

public class GameConfig {
    private GameConfig(){}
    public static final int WIDTH = 480;
    public static final int HEIGHT  = 800;

    public static final float WORLD_WIDTH = 6.0f;
    public static final float WORLD_HEIGHT = 10.0f;

    public static final float HUD_WIDTH = 480;
    public static final float HUD_HEIGHT = 800;

    public static final float WORLD_CENTER_X = WORLD_WIDTH/2;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT/2;

    public static final float OBSTACLE_SPAWN_TIME = 0.25f; // default 0.25f
    public static final float OBSTACLE_DEFAULT_SPEED = 0.07f;

    public static final float MAX_PLAYER_X_SPEED = 0.15f; //how fast player moves left and right

    public static final int LIVES_AT_START = 3;

    public static final float OBSTACLE_EASY_SPEED = 0.07f;
    public static final float OBSTACLE_MEDIUM_SPEED = 0.10f;
    public static final float OBSTACLE_HARD_SPEED = 0.15f;

    public static final float PLAYER_BOUNDS_RADIUS = 0.3f;
    public static final float PLAYER_BOUNDS_DIMENSION = PLAYER_BOUNDS_RADIUS * 2;
    public static final float OBSTACLE_BOUNDS_RADIUS = 0.3f;
    public static final float OBSTACLE_BOUNDS_DIMENSION = 2 * OBSTACLE_BOUNDS_RADIUS;


}
