package com.mygdx.game.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

/**
 * Created by Jay Nguyen on 4/14/2017.
 */

public class StartUpSystem extends EntitySystem {

    private EntityFactorySystem entityFactorySystem;
    private SoundSystem soundSystem;

    public StartUpSystem(){

    }

    @Override
    public void addedToEngine(Engine engine) {
        entityFactorySystem = engine.getSystem(EntityFactorySystem.class);
        entityFactorySystem.addBackground();
        entityFactorySystem.addPlayer();
        soundSystem = engine.getSystem(SoundSystem.class);
        soundSystem.playThemeSong();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public boolean checkProcessing() {
        return false;
    }
}
