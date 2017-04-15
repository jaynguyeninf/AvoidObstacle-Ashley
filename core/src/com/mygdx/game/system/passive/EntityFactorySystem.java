package com.mygdx.game.system.passive;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.AssetPaths;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.CleanUpComponent;
import com.mygdx.game.components.LifeCollectibleComponent;
import com.mygdx.game.components.ScoreCollectibleComponent;
import com.mygdx.game.components.SizeComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.WorldWrapperComponent;
import com.mygdx.game.configurations.GameConfig;

public class EntityFactorySystem extends EntitySystem {

    private final AssetManager assetManager;

    private PooledEngine engine;
    private TextureAtlas gameplayAtlas;

    public EntityFactorySystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public boolean checkProcessing() {
        return false;
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = (PooledEngine)engine;
    }

    public void addPlayer() {
        float startX = GameConfig.WORLD_CENTER_X - GameConfig.PLAYER_BOUNDS_RADIUS;
        float startY = 1 - GameConfig.PLAYER_BOUNDS_DIMENSION / 2;

        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.set(startX, startY, GameConfig.PLAYER_BOUNDS_RADIUS); //set starting position

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);

        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        positionComponent.x = startX;//restart at start position
        positionComponent.y = startY;

        WorldWrapperComponent worldWrapperComponent = engine.createComponent(WorldWrapperComponent.class);

        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.textureRegion = gameplayAtlas.findRegion(AssetPaths.PLAYER_REGION);

        SizeComponent sizeComponent = engine.createComponent(SizeComponent.class);
        sizeComponent.width = GameConfig.PLAYER_BOUNDS_DIMENSION;
        sizeComponent.height = GameConfig.PLAYER_BOUNDS_DIMENSION;

        Entity entity = engine.createEntity();
        entity.add(boundsComponent);
        entity.add(movementComponent);
        entity.add(playerComponent);
        entity.add(positionComponent);
        entity.add(worldWrapperComponent);
        entity.add(textureComponent);
        entity.add(sizeComponent);
        engine.addEntity(entity);

    }

    public void addObstacle(float x, float y) { //x,y represent spawn locations
        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;

        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.set(x, y, GameConfig.OBSTACLE_BOUNDS_RADIUS);

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.ySpeed = -GameManager.INSTANCE.getDifficultyLevel().getObstacleSpeed();//set obstacles' speed

        CleanUpComponent cleanUpComponent = engine.createComponent(CleanUpComponent.class);

        ObstacleComponent obstacleComponent = engine.createComponent(ObstacleComponent.class);

        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.textureRegion = gameplayAtlas.findRegion(AssetPaths.OBSTACLE_REGION);

        SizeComponent sizeComponent = engine.createComponent(SizeComponent.class);
        sizeComponent.width = GameConfig.OBSTACLE_BOUNDS_DIMENSION;
        sizeComponent.height = GameConfig.OBSTACLE_BOUNDS_DIMENSION;


        Entity entity = engine.createEntity();
        entity.add(positionComponent);
        entity.add(movementComponent);
        entity.add(boundsComponent);
        entity.add(cleanUpComponent);
        entity.add(obstacleComponent);
        entity.add(textureComponent);
        entity.add(sizeComponent);
        engine.addEntity(entity);
    }

    public void addLifeCollectible(float x, float y) { //x,y represent spawn locations
        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;

        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.set(x, y, GameConfig.LIFE_COLLECTIBLE_BOUNDS_RADIUS);

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.ySpeed = -GameConfig.LIFE_COLLECTIBLE_SPEED;//set speed

        CleanUpComponent cleanUpComponent = engine.createComponent(CleanUpComponent.class);

        LifeCollectibleComponent lifeCollectibleComponent = engine.createComponent(LifeCollectibleComponent.class);

        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.textureRegion = gameplayAtlas.findRegion(AssetPaths.LIFE_COLLECTIBLE);

        SizeComponent sizeComponent = engine.createComponent(SizeComponent.class);
        sizeComponent.width = GameConfig.LIFE_COLLECTIBLE_BOUNDS_DIMENSION;
        sizeComponent.height = GameConfig.LIFE_COLLECTIBLE_BOUNDS_DIMENSION;


        Entity entity = engine.createEntity();
        entity.add(positionComponent);
        entity.add(movementComponent);
        entity.add(boundsComponent);
        entity.add(cleanUpComponent);
        entity.add(lifeCollectibleComponent);
        entity.add(textureComponent);
        entity.add(sizeComponent);
        engine.addEntity(entity);
    }

    public void addScoreCollectible(float x, float y) { ///x,y represent spawn locations
        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;

        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.set(x, y, GameConfig.SCORE_COLLECTIBLE_BOUNDS_RADIUS);

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.ySpeed = -GameConfig.SCORE_COLLECTIBLE_SPEED;//set speed

        CleanUpComponent cleanUpComponent = engine.createComponent(CleanUpComponent.class);

        ScoreCollectibleComponent scoreCollectibleComponent = engine.createComponent(ScoreCollectibleComponent.class);

        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.textureRegion = gameplayAtlas.findRegion(AssetPaths.SCORE_COLLECTIBLE);

        SizeComponent sizeComponent = engine.createComponent(SizeComponent.class);
        sizeComponent.width = GameConfig.SCORE_COLLECTIBLE_BOUNDS_DIMENSION;
        sizeComponent.height = GameConfig.SCORE_COLLECTIBLE_BOUNDS_DIMENSION;


        Entity entity = engine.createEntity();
        entity.add(positionComponent);
        entity.add(movementComponent);
        entity.add(boundsComponent);
        entity.add(cleanUpComponent);
        entity.add(scoreCollectibleComponent);
        entity.add(textureComponent);
        entity.add(sizeComponent);
        engine.addEntity(entity);
    }

    public void addBackground() {
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.textureRegion = gameplayAtlas.findRegion(AssetPaths.BACKGROUND_REGION);

        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        positionComponent.x = 0;
        positionComponent.y = 0;

        SizeComponent sizeComponent = engine.createComponent(SizeComponent.class);
        sizeComponent.width = GameConfig.WORLD_WIDTH;
        sizeComponent.height = GameConfig.WORLD_HEIGHT;

        Entity entity = engine.createEntity();
        entity.add(textureComponent);
        entity.add(positionComponent);
        entity.add(sizeComponent);
        engine.addEntity(entity);
    }
}
