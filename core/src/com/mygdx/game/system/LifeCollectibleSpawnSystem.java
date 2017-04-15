package com.mygdx.game.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.system.passive.EntityFactorySystem;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 4/3/2017.
 */

public class LifeCollectibleSpawnSystem extends IntervalSystem {

    private  EntityFactorySystem factory;

    public LifeCollectibleSpawnSystem() {
        super(MathUtils.random(5,10)); // default: 15-30 seconds to spawn
    }

    @Override
    public void addedToEngine(Engine engine) {
        factory = engine.getSystem(EntityFactorySystem.class);
    }

    @Override
    protected void updateInterval() {
        float minX = 0;
        float maxX = GameConfig.WORLD_WIDTH - GameConfig.LIFE_COLLECTIBLE_BOUNDS_DIMENSION;

        float spawnX = MathUtils.random(minX, maxX);
        float spawnY = GameConfig.WORLD_HEIGHT;

        factory.addLifeCollectible(spawnX, spawnY);
    }
}
