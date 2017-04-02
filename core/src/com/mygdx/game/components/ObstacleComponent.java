package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Jay Nguyen on 3/31/2017.
 */

public class ObstacleComponent implements Component, Pool.Poolable {
    public boolean hit;
    public boolean passed;

    @Override
    public void reset() {
        hit = false; //reset to default value because it's set to true in CollisionSystem
        passed = false;
    }
}
