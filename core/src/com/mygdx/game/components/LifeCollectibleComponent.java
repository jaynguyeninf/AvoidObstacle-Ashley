package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class LifeCollectibleComponent implements Component, Pool.Poolable {

    public boolean lifeCollected;

    @Override
    public void reset() {
        lifeCollected = false;
    }
}
