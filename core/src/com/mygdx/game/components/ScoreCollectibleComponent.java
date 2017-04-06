package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ScoreCollectibleComponent implements Component, Pool.Poolable {

    public boolean scoreCollected;

    @Override
    public void reset() {
        scoreCollected = false;
    }
}
