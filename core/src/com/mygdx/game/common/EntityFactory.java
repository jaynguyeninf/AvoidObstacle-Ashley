package com.mygdx.game.common;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.MovementComponent;
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
        positionComponent.x = startX;
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
}
