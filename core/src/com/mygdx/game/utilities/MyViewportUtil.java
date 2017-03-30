package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyViewportUtil {
    private MyViewportUtil(){
    }
    private static final Logger log = new Logger(MyViewportUtil.class.getName(), Logger.DEBUG);
    private static final int DEFAULT_CELL_SIZE = 1;


    public static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer){
        drawGrid(viewport, shapeRenderer, DEFAULT_CELL_SIZE);

    }

    public static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer, int cellSize){
        //validate parameters
        if(viewport == null){
            throw new IllegalArgumentException("viewport parameter is required");
        }
        if(shapeRenderer == null){
            throw new IllegalArgumentException("shapeRenderer parameter is required");
        }
        if(cellSize < DEFAULT_CELL_SIZE){
            cellSize = DEFAULT_CELL_SIZE;
        }

        //copy old color from render
//        shapeRenderer.getColor().cpy();
        Color oldColor = new Color(shapeRenderer.getColor());

        int worldWidth = (int) viewport.getWorldWidth();
        int worldHeight = (int) viewport.getWorldHeight();
        int doubleWorldWidth = worldWidth * 2;
        int doubleWorldHeight = worldHeight * 2;

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        //draw vertical lines
        for(int x = -doubleWorldWidth; x < doubleWorldWidth; x += cellSize){
            shapeRenderer.line(x, -doubleWorldHeight, x, doubleWorldHeight);
        }

        //draw horizontal lines
        for(int y = -doubleWorldHeight; y < doubleWorldHeight; y+= cellSize){
            shapeRenderer.line(-doubleWorldWidth, y, doubleWorldWidth, y);
        }

        //draw x and y axis
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(0, -doubleWorldHeight, 0, doubleWorldHeight);
        shapeRenderer.line(-doubleWorldWidth, 0, doubleWorldWidth, 0);

        //draw world bounds (only able see when zoomed out)
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(0, worldHeight, worldWidth, worldHeight);
        shapeRenderer.line(worldWidth, 0, worldWidth, worldHeight);

        shapeRenderer.end();
        shapeRenderer.setColor(oldColor);
    }

    public static void debugPixelPerUnit(Viewport viewport){
        if(viewport == null){
            throw  new IllegalArgumentException("viewport parameter is required");
        }

        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //PPU = Pixels per unit
        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

        log.debug("x PPU= " + xPPU + "y PPU= " +yPPU);
    }

    /**
     * Created by Vu on 1/26/2017.
     */

    public static class MyUtility {
        private MyUtility(){
        }

        public static void clearScreen(){
            clearScreen(Color.BLACK);
        }
        public static void clearScreen(Color color){
            Gdx.gl.glClearColor(color.r,color.g,color.b,color.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
    }
}
