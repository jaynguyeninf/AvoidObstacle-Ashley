package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.PositionComponent;

/**
 * Created by Jay Nguyen on 3/30/2017.
 */

public class BoundsSystem extends IteratingSystem {


    private static final Family FAMILY = Family.all(
//            BoundsComponent.class,
//            PositionComponent.class
    ).get();

    public BoundsSystem(){
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent boundsComponent = Mappers.BOUNDS_COMPONENT.get(entity);
        PositionComponent positionComponent = Mappers.POSITION_COMPONENT.get(entity);

        boundsComponent.bounds.x = positionComponent.x;
        boundsComponent.bounds.y = positionComponent.y;
    }
}
