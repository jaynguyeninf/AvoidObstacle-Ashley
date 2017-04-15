package com.mygdx.game.screens.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.screens.menu.MenuScreen;
import com.mygdx.game.system.BoundsSystem;
import com.mygdx.game.system.CleanUpSystem;
import com.mygdx.game.system.HudRenderSystem;
import com.mygdx.game.system.LifeCollectibleSpawnSystem;
import com.mygdx.game.system.MovementSystem;
import com.mygdx.game.system.ObstacleSpawnSystem;
import com.mygdx.game.system.ScoreCollectibleSpawnSystem;
import com.mygdx.game.system.ScoreSystem;
import com.mygdx.game.system.TextureRenderSystem;
import com.mygdx.game.system.TouchInputSystem;
import com.mygdx.game.system.WorldWrapperSystem;
import com.mygdx.game.system.collisions.CollisionSystem;
import com.mygdx.game.system.debug.DebugCameraSystem;
import com.mygdx.game.system.debug.DebugRenderSystem;
import com.mygdx.game.system.debug.GridRenderSystem;
import com.mygdx.game.system.passive.EntityFactorySystem;
import com.mygdx.game.system.passive.SoundSystem;
import com.mygdx.game.system.passive.StartUpSystem;

/**
 * Created by Vu on 2/6/2017.
 */

public class GameScreen implements Screen {
    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);
    private static final boolean DEBUG = false;

    private final AvoidObstacleGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private PooledEngine engine; //PooledEngine will take care of traditional pooling
    private EntityFactorySystem entityFactorySystem;

    private boolean reset;

    public GameScreen(AvoidObstacleGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT); //internal camera
        shapeRenderer = new ShapeRenderer();
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        engine = new PooledEngine();
        entityFactorySystem = new EntityFactorySystem(assetManager);

        //add systems to engine //optional: call super() in system to set priority
        engine.addSystem(entityFactorySystem);
        engine.addSystem(new SoundSystem(assetManager));
//        engine.addSystem(new KeyInputSystem());
        engine.addSystem(new TouchInputSystem(viewport));

        engine.addSystem(new MovementSystem());
        engine.addSystem(new WorldWrapperSystem(viewport)); //before BoundsSystem
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new ObstacleSpawnSystem());
        engine.addSystem(new LifeCollectibleSpawnSystem());
        engine.addSystem(new ScoreCollectibleSpawnSystem());
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new ScoreSystem());


        engine.addSystem(new TextureRenderSystem(viewport, game.getBatch()));

        if (DEBUG) { //group all debugging systems
            engine.addSystem(new DebugCameraSystem(camera, GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y));
            engine.addSystem(new GridRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugRenderSystem(viewport, shapeRenderer));
        }

        engine.addSystem(new HudRenderSystem(hudViewport, font, game.getBatch()));
        engine.addSystem(new StartUpSystem());
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //this will call update() on all systems
        engine.update(delta);
        //switch screen when game is over
        if (GameManager.INSTANCE.isGameOver()) {
            GameManager.INSTANCE.reset(); //reset score and lives otherwise game stay in Menu Screen
            game.setScreen(new MenuScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        engine.removeAllEntities();
        log.debug("total entities when disposed = " + engine.getEntities().size());

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
