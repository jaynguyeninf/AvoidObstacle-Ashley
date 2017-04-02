package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.CleanUpComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 3/31/2017.
 */

public class CleanUpSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            CleanUpComponent.class
    ).get();

    public CleanUpSystem(){
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = Mappers.POSITION_COMPONENT.get(entity);

        if(positionComponent.y < - GameConfig.OBSTACLE_BOUNDS_DIMENSION){ //remove entities that are slightly below x axis
            getEngine().removeEntity(entity); //remove entity after all processes are done
        }
    }
}
