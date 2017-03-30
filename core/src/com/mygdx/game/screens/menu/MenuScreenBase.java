package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.utilities.MyViewportUtil;

/**
 * Created by Jay Nguyen on 3/21/2017.
 */

public abstract class MenuScreenBase extends ScreenAdapter {
    protected final AvoidObstacleGame game;
    protected final AssetManager assetManager;
    private Stage stage;
    private Viewport viewport;

    public MenuScreenBase(AvoidObstacleGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }
    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        stage.addActor(createUI());
    }

    protected abstract Actor createUI();

    @Override
    public void render(float delta) {
        MyViewportUtil.MyUtility.clearScreen();

        stage.act();
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
