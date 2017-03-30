package com.mygdx.game.configurations;



public enum DifficultyLevel {

    EASY(GameConfig.OBSTACLE_EASY_SPEED),
    MEDIUM(GameConfig.OBSTACLE_MEDIUM_SPEED),
    HARD(GameConfig.OBSTACLE_HARD_SPEED);

    private final float obstacleSpeed;

    DifficultyLevel(float obstacleSpeed) {
        this.obstacleSpeed = obstacleSpeed;
    }

    public float getObstacleSpeed() {
        return obstacleSpeed;
    }

    //return true if current difficulty is easy ||medium || hard
    public boolean isEasy(){
        return this == EASY;
    }

    public boolean isMedium(){
        return this == MEDIUM;
    }

    public boolean isHard(){
        return this == HARD;
    }
}
