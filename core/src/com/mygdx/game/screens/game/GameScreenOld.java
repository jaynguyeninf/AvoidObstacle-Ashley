package com.mygdx.game.screens.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.screens.menu.MenuScreen;

/**
 * Created by Vu on 2/6/2017.
 */
@Deprecated

public class GameScreenOld implements Screen {
    private static final Logger log = new Logger(GameScreenOld.class.getName(), Logger.DEBUG);

    private final AvoidObstacleGame game;
    private final AssetManager assetManager;
    private GameController gameController;
    private GameRenderer gameRenderer;

    public GameScreenOld(AvoidObstacleGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        log.debug("show");
        gameController = new GameController(game);
        gameRenderer = new GameRenderer(game.getBatch(), assetManager, gameController);
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render(delta);

        //if check in update() in game controller, game will crash since gameRenderer still be called
        if(gameController.isGameOver()){
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
