package com.mygdx.game.common;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.CleanUpComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.WorldWrapperComponent;
import com.mygdx.game.configurations.GameConfig;

public class EntityFactory {
    private final PooledEngine engine;

    public EntityFactory(PooledEngine pooledEngine) {
        this.engine = pooledEngine;
    }

    public void addPlayer() {
        float startX = GameConfig.WORLD_CENTER_X;
        float startY = GameConfig.PLAYER_BOUNDS_DIMENSION;

        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.set(startX, startY, GameConfig.PLAYER_BOUNDS_RADIUS); //set starting position

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);

        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        positionComponent.x = startX;//restart at start position
        positionComponent.y = startY;

        WorldWrapperComponent worldWrapperComponent = engine.createComponent(WorldWrapperComponent.class);

        Entity entity = engine.createEntity();
        entity.add(boundsComponent);
        entity.add(movementComponent);
        entity.add(playerComponent);
        entity.add(positionComponent);
        entity.add(worldWrapperComponent);
        engine.addEntity(entity);

    }

    public void addObstacle(float x, float y) { //x,y represent spawn X locations
        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        positionComponent.x = x;
        positionComponent.y = y;

        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.set(x, y, GameConfig.OBSTACLE_BOUNDS_RADIUS);

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.ySpeed = - GameManager.INSTANCE.getDifficultyLevel().getObstacleSpeed();//set obstacles' speed

        CleanUpComponent cleanUpComponent = engine.createComponent(CleanUpComponent.class);

        ObstacleComponent obstacleComponent = engine.createComponent(ObstacleComponent.class);


        Entity entity = engine.createEntity();
        entity.add(positionComponent);
        entity.add(movementComponent);
        entity.add(boundsComponent);
        entity.add(cleanUpComponent);
        entity.add(obstacleComponent);
        engine.addEntity(entity);

    }
}
