package com.mygdx.game.system.collisions;

/**
 * Created by Jay Nguyen on 4/1/2017.
 */

public interface CollisionListener {

    void hitObstacle();
    void hitLifeCollectible();
    void hitScoreCollectible();
}
