package com.mygdx.game.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.system.passive.EntityFactorySystem;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 4/3/2017.
 */

public class ScoreCollectibleSpawnSystem extends IntervalSystem {

    private EntityFactorySystem factory;

    public ScoreCollectibleSpawnSystem() {
        super(MathUtils.random(3, 6)); //default: 5;15
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

        factory.addScoreCollectible(spawnX, spawnY);

    }
}
