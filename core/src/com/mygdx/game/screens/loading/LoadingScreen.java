package com.mygdx.game.screens.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.screens.menu.MenuScreen;

/**
 * Created by Vu on 2/24/2017.
 */

public class LoadingScreen extends ScreenAdapter {
    private static final Logger log = new Logger(LoadingScreen.class.getName(), Logger.DEBUG);

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2;
    private static final float PROGRESS_BAR_HEIGHT = 60f;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen; //flag used to fix loading screen issue

    private final AvoidObstacleGame game;
    private final AssetManager assetManager;


    private Stage stage;

    //constructor
    public LoadingScreen(AvoidObstacleGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        stage = new Stage(viewport);

        //load assets
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.finishLoading();


        Skin skin = assetManager.get(AssetDescriptors.UI_SKIN);
        Label label  = new Label("Loading Screen", skin);
        Table table = new Table();
        table.add(label).bottom();
        table.pack();
        stage.addActor(table);


        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.GAMEPLAY_ATLAS);
        assetManager.load(AssetDescriptors.HIT_SOUND);

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Draw Rectangle
        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        draw();
        shapeRenderer.end();

        stage.act();
        stage.draw();

        //call last to dispose LoadingScreen
        if(changeScreen){
            game.setScreen(new MenuScreen(game));
        }
    }

    private void update(float delta){

        progress = assetManager.getProgress(); //get progress percentage, between 0 and 1

        //update returns true when all assets are loaded
        if(assetManager.update()){
            waitTime -= delta; //decrement from .75 to 0.00

            if(waitTime <= 0){
                changeScreen = true; // flag to fix loading issue
            }
        }
    }

    private void draw(){
        float progessBarXPosition = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2;
        float progessBarYPosition = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2;

        shapeRenderer.rect(progessBarXPosition, progessBarYPosition,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT); //multiple progress otherwise it show 1 complete bar
    }

    private static void waitMilliseconds(long milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override
    public void hide() {
        // NOTE: screens don't dispose automatically
        dispose();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
