package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionComponent;

/**
 * Created by Jay Nguyen on 3/30/2017.
 */

public class MovementSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            MovementComponent.class
    ).get();

    public MovementSystem(){
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = Mappers.POSITION_COMPONENT.get(entity);
        MovementComponent movementComponent = Mappers.MOVEMENT_COMPONENT.get(entity);

        positionComponent.x += movementComponent.xSpeed;
        positionComponent.y += movementComponent.ySpeed;
    }
}
