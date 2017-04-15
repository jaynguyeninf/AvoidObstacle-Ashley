package com.mygdx.game.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.system.passive.EntityFactorySystem;

/**
 * Created by Jay Nguyen on 3/30/2017.
 */



public class ObstacleSpawnSystem extends IntervalSystem {

    private EntityFactorySystem factory;

    public ObstacleSpawnSystem() {
        super(GameConfig.OBSTACLE_SPAWN_TIME);
    }

    @Override
    public void addedToEngine(Engine engine) {
        factory = engine.getSystem(EntityFactorySystem.class);
    }

    @Override
    protected void updateInterval() {

        float minX = 0;
        float maxX = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_BOUNDS_DIMENSION;

        float obstacleSpawnX = MathUtils.random(minX, maxX);
        float obstacleSpawnY = GameConfig.WORLD_HEIGHT;

        factory.addObstacle(obstacleSpawnX, obstacleSpawnY);
    }
}
