package com.mygdx.game.system;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 4/3/2017.
 */

public class LifeCollectibleSpawnSystem extends IntervalSystem {

    private final EntityFactory factory;

    public LifeCollectibleSpawnSystem(EntityFactory factory) {
        super(MathUtils.random(5,10)); // 5-10 seconds to spawn
        this.factory = factory;
    }

    @Override
    protected void updateInterval() {
        float minX = 0;
        float maxX = GameConfig.WORLD_WIDTH - GameConfig.LIVES_COIN_BOUNDS_DIMENSION;

        float spawnX  = MathUtils.random(minX,maxX);
        float spawnY = GameConfig.WORLD_HEIGHT;

        factory.addLifeCollectible(spawnX, spawnY);
    }
}
