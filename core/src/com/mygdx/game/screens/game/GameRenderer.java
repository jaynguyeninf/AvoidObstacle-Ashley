package com.mygdx.game.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.AssetPaths;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.entities.Background;
import com.mygdx.game.entities.Obstacle;
import com.mygdx.game.entities.Player;
import com.mygdx.game.utilities.MyViewportUtil;
import com.mygdx.game.utilities.debug.DebugCameraController;

@Deprecated
public class GameRenderer implements Disposable {

    private static final Logger log = new Logger(Game.class.getName(), Logger.DEBUG);
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private BitmapFont font;
    private DebugCameraController debugCameraController;
    private final GlyphLayout glyphLayout = new GlyphLayout(); //do not need to create this in every frame

    private final GameController gameController;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private TextureRegion playerTextureRegion, obstacleTextureRegion, backgroundTextureRegion;
    private TextureAtlas textureAtlas;


    public GameRenderer(SpriteBatch batch, AssetManager assetManager, GameController gameController){
        this.batch = batch;
        this.assetManager = assetManager;
        this.gameController = gameController;

        init();
    }

    public void init() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HEIGHT, hudCamera);

        //get assets
        font = assetManager.get(AssetDescriptors.FONT);
        textureAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        backgroundTextureRegion = textureAtlas.findRegion(AssetPaths.BACKGROUND_REGION);
        obstacleTextureRegion = textureAtlas.findRegion(AssetPaths.OBSTACLE_REGION);
        playerTextureRegion = textureAtlas.findRegion(AssetPaths.PLAYER_REGION);

        //instantiate debugCameraController
        debugCameraController = new DebugCameraController();
        //if not set, the camera centers the x and y axis, because Vector2 has 0,0 default even though we have center the viewport.
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);


    }

    public void render(float delta){

        //batch.totalRenderCalls = 0; //used to debug total render calls

        debugCameraController.handleInputDebug(delta);
        debugCameraController.applyTo(camera);

        if(Gdx.input.isTouched() && !gameController.isGameOver()){
            Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(screenTouch.cpy()); //use cpy() or new Vector2
            //or Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));

            Player player = gameController.getPlayer();
            worldTouch.x =  MathUtils.clamp(worldTouch.x, 0, GameConfig.WORLD_WIDTH - player.getWidth());
            player.setX(worldTouch.x);      //update player's coords
        }


        //clear screen
        MyViewportUtil.MyUtility.clearScreen();

        //draw textures
        renderGameplay();

        //render ui/hud
        renderUI();

        //render debug graphics
        shapeRendererDebug();

    }

    private void shapeRendererDebug(){
//        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawDebug();
        shapeRenderer.end();

        MyViewportUtil.drawGrid(viewport, shapeRenderer);
    }

    private void drawDebug(){
        Player player = gameController.getPlayer();
        player.drawDebug(shapeRenderer);

        Array<Obstacle> obstacles = gameController.getObstacles();
        for(Obstacle obstacle : obstacles){
            obstacle.drawDebug(shapeRenderer);
        }
    }

    private void renderGameplay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Background background = gameController.getBackground();
        batch.draw(backgroundTextureRegion, background.getX(), background.getY(), background.getWidth(), background.getHeight());

        Player player = gameController.getPlayer();
        batch.draw(playerTextureRegion, player.getX(), player.getY(), player.getWidth(), player.getHeight());

        for(Obstacle obstacle : gameController.getObstacles())
        batch.draw(obstacleTextureRegion, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        batch.end();
    }

    private void renderUI(){
        hudViewport.apply(); //tell GL viewport we want to use this viewport
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        String livesText = "LIVES: " +gameController.getLives();
        glyphLayout.setText(font, livesText);
        font.draw(batch, livesText, 20, GameConfig.HUD_HEIGHT - glyphLayout.height);

        String scoreText = "SCORE: " +gameController.getScore();
        glyphLayout.setText(font, scoreText);
        font.draw(batch, scoreText, (GameConfig.HUD_WIDTH - glyphLayout.width - 10), GameConfig.HUD_HEIGHT - glyphLayout.height);

        batch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        MyViewportUtil.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
