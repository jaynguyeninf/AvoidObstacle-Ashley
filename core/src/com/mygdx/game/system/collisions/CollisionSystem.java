package com.mygdx.game.system.collisions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PlayerComponent;

/**
 * Created by Jay Nguyen on 3/31/2017.
 */

public class CollisionSystem extends EntitySystem {
    private static final Logger log = new Logger(CollisionSystem.class.getName(), Logger.DEBUG);
    private final CollisionListener listener;

    private static final Family PLAYER_FAMILY = Family.all(
            PlayerComponent.class,
            BoundsComponent.class
    ).get();

    private static final Family OBSTACLE_FAMILY = Family.all(
            ObstacleComponent.class,
            BoundsComponent.class
    ).get();

    public CollisionSystem(CollisionListener listener) {
        this.listener = listener;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER_FAMILY);
        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor(OBSTACLE_FAMILY);

        for (Entity playerEntity : players) {
            for (Entity obstacleEntity : obstacles) {
                ObstacleComponent obstacleComponent = Mappers.OBSTACLE_COMPONENT.get(obstacleEntity);

                if (obstacleComponent.hit) {
                    continue;
                }

                if (checkCollision(playerEntity, obstacleEntity)) {
                    obstacleComponent.hit = true;
                    log.debug("Player Hit Obstacle");
                    listener.hitObstacle();
                }
            }
        }
    }

    private boolean checkCollision(Entity player, Entity obstacle) {
        BoundsComponent playerBounds = Mappers.BOUNDS_COMPONENT.get(player);
        BoundsComponent obstacleBounds = Mappers.BOUNDS_COMPONENT.get(obstacle);

        return Intersector.overlaps(playerBounds.bounds, obstacleBounds.bounds);
    }
}
