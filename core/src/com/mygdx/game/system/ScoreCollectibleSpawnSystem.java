package com.mygdx.game.system;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 4/3/2017.
 */

public class ScoreCollectibleSpawnSystem extends IntervalSystem {

    private final EntityFactory factory;

    public ScoreCollectibleSpawnSystem(EntityFactory factory) {
        super(MathUtils.random(3,6)); //default: 5;15
        this.factory = factory;
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
