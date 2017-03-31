package com.mygdx.game.screens.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.common.EntityFactory;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.system.BoundsSystem;
import com.mygdx.game.system.MovementSystem;
import com.mygdx.game.system.UserInputSystem;
import com.mygdx.game.system.WorldWrapperSystem;
import com.mygdx.game.system.debug.DebugCameraSystem;
import com.mygdx.game.system.debug.DebugRenderSystem;
import com.mygdx.game.system.debug.GridRenderSystem;

/**
 * Created by Vu on 2/6/2017.
 */

public class GameScreen implements Screen {
    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);

    private final AvoidObstacleGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private PooledEngine engine; //PooledEngine will take care of traditional pooling
    private EntityFactory entityFactory;

    public GameScreen(AvoidObstacleGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        engine = new PooledEngine();

        entityFactory = new EntityFactory(engine);

        //add systems to engine //optional: call super() in system to set priority
        engine.addSystem(new DebugCameraSystem(camera, GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y));
        engine.addSystem(new UserInputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new WorldWrapperSystem(viewport)); //before BoundsSystem
        engine.addSystem(new BoundsSystem());

        engine.addSystem(new GridRenderSystem(viewport, shapeRenderer));
        engine.addSystem(new DebugRenderSystem(viewport,shapeRenderer));

        entityFactory.addPlayer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //this will call update() on all systems
        engine.update(delta);
        log.debug("entities size = " + engine.getEntities().size());


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
        shapeRenderer.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
