package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 4/1/2017.
 */

public class ScoreSystem extends EntitySystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            ObstacleComponent.class
    ).get();


    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor(FAMILY);

        for(Entity entity : obstacles) {
            PositionComponent positionComponent = Mappers.POSITION_COMPONENT.get(entity);
            ObstacleComponent obstacleComponent = Mappers.OBSTACLE_COMPONENT.get(entity);
            if(positionComponent.y < GameConfig.PLAYER_BOUNDS_DIMENSION && !obstacleComponent.passed){
                GameManager.INSTANCE.updateScore(1);
                obstacleComponent.passed = true;
            }
        }
    }
}
