package com.mygdx.game.screens.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.EntityFactory;
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
import com.mygdx.game.system.UserInputSystem;
import com.mygdx.game.system.WorldWrapperSystem;
import com.mygdx.game.system.collisions.CollisionListener;
import com.mygdx.game.system.collisions.CollisionSystem;
import com.mygdx.game.system.debug.DebugCameraSystem;
import com.mygdx.game.system.debug.DebugRenderSystem;
import com.mygdx.game.system.debug.GridRenderSystem;

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
    private EntityFactory entityFactory;
    private Sound hitSound, collectLifeSound, collectScoreSound;

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
        engine = new PooledEngine();
        entityFactory = new EntityFactory(engine, assetManager);
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        hitSound = assetManager.get(AssetDescriptors.HIT_SOUND);
        collectLifeSound = assetManager.get(AssetDescriptors.COLLECT_LIFE_SOUND);
        collectScoreSound = assetManager.get(AssetDescriptors.COLLECT_SCORE_SOUND);

        //implemented abstract methods from CollisionListener
        CollisionListener listener = new CollisionListener() {
            @Override
            public void hitObstacle() {
                GameManager.INSTANCE.decreaseLives();
                hitSound.play();

                if (GameManager.INSTANCE.isGameOver()) {
                    GameManager.INSTANCE.updateHighScore();
                } else {
                    //removeAllEntities() uses scheduledForRemoval which means it is called only when its system's update() is done.
                    engine.removeAllEntities();
                    reset = true;
                }
            }

            @Override
            public void hitLifeCollectible() {
                GameManager.INSTANCE.increaseLives();
                collectLifeSound.play();

            }

            @Override
            public void hitScoreCollectible() {
                int scoreValue = MathUtils.random(5, 50);
                GameManager.INSTANCE.updateScore(scoreValue);
                collectScoreSound.play();
                log.debug("score collected = " + scoreValue);
            }
        };

        //add systems to engine //optional: call super() in system to set priority
        engine.addSystem(new UserInputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new WorldWrapperSystem(viewport)); //before BoundsSystem
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new ObstacleSpawnSystem(entityFactory));
        engine.addSystem(new LifeCollectibleSpawnSystem(entityFactory));
        engine.addSystem(new ScoreCollectibleSpawnSystem(entityFactory));
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new CollisionSystem(listener));
        engine.addSystem(new ScoreSystem());

        engine.addSystem(new TextureRenderSystem(viewport, game.getBatch()));

        if (DEBUG) { //group all debugging systems
            engine.addSystem(new DebugCameraSystem(camera, GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y));
            engine.addSystem(new GridRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugRenderSystem(viewport, shapeRenderer));
        }


        engine.addSystem(new HudRenderSystem(hudViewport, font, game.getBatch()));

        customAddEntities();

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

        ///called after update() because of removeAllEntities uses scheduledForRemoval
        if (reset) {
            reset = false;
            customAddEntities();
        }

    }

    private void customAddEntities() {
        entityFactory.addBackground();
        entityFactory.addPlayer();
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
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
