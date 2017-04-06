package com.mygdx.game.configurations;

/**
 * Created by Vu on 1/26/2017.
 */

public class GameConfig {
    private GameConfig() {
    }

    //=========Desktop Launcher=========
    public static final int DESKTOP_WIDTH = 480; //pixels
    public static final int DESKTOP_HEIGHT = 800;

    //=========Gameplay=======
    public static final float WORLD_WIDTH = 6.0f; //world units
    public static final float WORLD_HEIGHT = 10.0f;

    //=========HUD/GUI========
    public static final float HUD_WIDTH = 480;
    public static final float HUD_HEIGHT = 800;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2;

    public static final float OBSTACLE_SPAWN_TIME = 0.25f;
    public static final float OBSTACLE_DEFAULT_SPEED = 0.07f;

    public static final float MAX_PLAYER_X_SPEED = 0.15f; //how fast player moves left and right

    public static final int LIVES_AT_START = 3;

    //=============Speed============
    public static final float OBSTACLE_EASY_SPEED = 0.07f;
    public static final float OBSTACLE_MEDIUM_SPEED = 0.10f;
    public static final float OBSTACLE_HARD_SPEED = 0.15f;
    public static final float LIFE_COLLECTIBLE_SPEED = 0.20f;
    public static final float SCORE_COLLECTIBLE_SPEED = 0.03f;

    //=============Bounds===============
    public static final float PLAYER_BOUNDS_RADIUS = 0.3f;
    public static final float PLAYER_BOUNDS_DIMENSION = PLAYER_BOUNDS_RADIUS * 2;
    public static final float OBSTACLE_BOUNDS_RADIUS = 0.3f;
    public static final float OBSTACLE_BOUNDS_DIMENSION = 2 * OBSTACLE_BOUNDS_RADIUS;
    public static final float LIFE_COLLECTIBLE_BOUNDS_RADIUS = 0.15f;
    public static final float LIFE_COLLECTIBLE_BOUNDS_DIMENSION = LIFE_COLLECTIBLE_BOUNDS_RADIUS * 2;
    public static final float SCORE_COLLECTIBLE_BOUNDS_RADIUS = 0.2f;
    public static final float SCORE_COLLECTIBLE_BOUNDS_DIMENSION = SCORE_COLLECTIBLE_BOUNDS_RADIUS * 2;


}
