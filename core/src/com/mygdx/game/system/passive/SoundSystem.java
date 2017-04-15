package com.mygdx.game.system.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.assets.AssetDescriptors;

/**
 * Created by Jay Nguyen on 4/14/2017.
 */
// == passive system ==
public class SoundSystem extends EntitySystem {

    private static final Logger log = new Logger(SoundSystem.class.getSimpleName(), Logger.DEBUG);

    private final AssetManager assetManager;

    private Sound hitSound, collectLifeSound, collectScoreSound;
    private Music themeSong;

    public SoundSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        hitSound = assetManager.get(AssetDescriptors.HIT_SOUND);
        collectLifeSound = assetManager.get(AssetDescriptors.COLLECT_LIFE_SOUND);
        collectScoreSound = assetManager.get(AssetDescriptors.COLLECT_SCORE_SOUND);
        themeSong = assetManager.get(AssetDescriptors.THEME_SONG);
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public boolean checkProcessing() {
        return false;
    }

    public void playHitSound() {
        hitSound.play();
    }

    public void playCollectLifeSound() {
        collectLifeSound.play();
    }

    public void playCollectScoreSound() {
        collectScoreSound.play();
    }

    public void playThemeSong(){
        themeSong.setVolume(0.2f);
        themeSong.play();
    }
}
