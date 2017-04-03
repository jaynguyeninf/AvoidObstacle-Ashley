package com.mygdx.game.system.collisions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.CleanUpComponent;
import com.mygdx.game.components.LifeCollectibleComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.PositionComponent;

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

    private static final Family LIVES_COIN_FAMILY = Family.all(
            LifeCollectibleComponent.class,
            BoundsComponent.class
    ).get();

    private static final Family CLEAN_UP_FAMILY = Family.all(
            LifeCollectibleComponent.class,
            BoundsComponent.class,
            CleanUpComponent.class
    ).get();


    public CollisionSystem(CollisionListener listener) {
        this.listener = listener;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER_FAMILY);
        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor(OBSTACLE_FAMILY);
        ImmutableArray<Entity> livesCoins = getEngine().getEntitiesFor(LIVES_COIN_FAMILY);
        ImmutableArray<Entity> cleanUpEntities = getEngine().getEntitiesFor(CLEAN_UP_FAMILY);

        //Check collision between player and obstacles
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

        //check collision between player and live coins
        for (Entity playerEntity : players) {
            for (Entity livesCoinEntity : livesCoins) {
                LifeCollectibleComponent lifeCollectibleComponent = Mappers.LIVES_COIN_COMPONENT.get(livesCoinEntity);

                for(Entity cleanUpEntity : cleanUpEntities) {
                    PositionComponent positionComponent = Mappers.POSITION_COMPONENT.get(livesCoinEntity);
                    if(checkCollision(playerEntity, cleanUpEntity)){ //remove entities that are slightly below x axis
                        getEngine().removeEntity(cleanUpEntity); //remove entity after all processes are done
                        log.debug("CLEANUPP");
                    }
                }


                if (lifeCollectibleComponent.hit) {
                    continue;
                }

                if (checkCollision(playerEntity, livesCoinEntity)) {
                    lifeCollectibleComponent.hit = true;
                    log.debug("Player Hit Lives Coin");
                    listener.hitLivesCoin();
                }
            }
        }

    }

    private boolean checkCollision(Entity player, Entity otherObjects) {
        BoundsComponent playerBounds = Mappers.BOUNDS_COMPONENT.get(player);
        BoundsComponent otherBounds = Mappers.BOUNDS_COMPONENT.get(otherObjects);

        return Intersector.overlaps(playerBounds.bounds, otherBounds.bounds);
    }
}
