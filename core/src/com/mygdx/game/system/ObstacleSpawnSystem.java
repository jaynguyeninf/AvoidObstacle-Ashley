package com.mygdx.game.system;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 3/30/2017.
 */

public class ObstacleSpawnSystem extends IntervalSystem {

    private final EntityFactory factory;

    public ObstacleSpawnSystem(EntityFactory factory) {
        super(GameConfig.OBSTACLE_SPAWN_TIME);
        this.factory = factory;
    }

    @Override
    protected void updateInterval() {

        float minX = 0;
        float maxX = GameConfig.WORLD_WIDTH;

        float obstacleSpawnX = MathUtils.random(minX, maxX);
        float obstacleSpawnY = GameConfig.WORLD_HEIGHT;

        factory.addObstacle(obstacleSpawnX, obstacleSpawnY);
    }
}
