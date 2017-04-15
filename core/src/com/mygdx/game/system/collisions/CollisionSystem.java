package com.mygdx.game.system.collisions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.LifeCollectibleComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.ScoreCollectibleComponent;
import com.mygdx.game.system.passive.EntityFactorySystem;
import com.mygdx.game.system.passive.SoundSystem;

/**
 * Created by Jay Nguyen on 3/31/2017.
 */

public class CollisionSystem extends EntitySystem {
    private static final Logger log = new Logger(CollisionSystem.class.getName(), Logger.DEBUG);

    private static final Family PLAYER_FAMILY = Family.all(
            PlayerComponent.class,
            BoundsComponent.class
    ).get();

    private static final Family OBSTACLE_FAMILY = Family.all(
            ObstacleComponent.class,
            BoundsComponent.class
    ).get();

    private static final Family LIFE_COLLECTIBLE_FAMILY = Family.all(
            LifeCollectibleComponent.class,
            BoundsComponent.class
    ).get();

    private static final Family SCORE_COLLECTIBLE_FAMILY = Family.all(
            BoundsComponent.class,
            ScoreCollectibleComponent.class
    ).get();

    private EntityFactorySystem entityFactorySystem;
    private SoundSystem soundSystem;

    public CollisionSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        soundSystem = engine.getSystem(SoundSystem.class);
        entityFactorySystem=engine.getSystem(EntityFactorySystem.class);
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER_FAMILY);
        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor(OBSTACLE_FAMILY);
        ImmutableArray<Entity> lifeCollectibles = getEngine().getEntitiesFor(LIFE_COLLECTIBLE_FAMILY);
        ImmutableArray<Entity> scoreCollectibles = getEngine().getEntitiesFor(SCORE_COLLECTIBLE_FAMILY);

        //Check collision between player and obstacles
        for (Entity playerEntity : players) {
            for (Entity obstacleEntity : obstacles) {
                ObstacleComponent obstacleComponent = Mappers.OBSTACLE_COMPONENT.get(obstacleEntity);

                if (obstacleComponent.hit) {
                    continue;
                }

                if (checkCollision(playerEntity, obstacleEntity)) {
                    obstacleComponent.hit = true;
                    soundSystem.playHitSound();
                    GameManager.INSTANCE.decreaseLives();

                    if (GameManager.INSTANCE.isGameOver()) {
                        GameManager.INSTANCE.updateHighScore();
                    } else {
                        getEngine().removeAllEntities();
                        entityFactorySystem.addBackground();
                        entityFactorySystem.addPlayer();
                    }
                }
            }
        }

        //check collision between player and life collectibles
        for (Entity playerEntity : players) {
            for (Entity lifeCollectible : lifeCollectibles) {

                LifeCollectibleComponent lifeCollectibleComponent = Mappers.LIFE_COLLECTIBLE_COMPONENT.get(lifeCollectible);

                if (lifeCollectibleComponent.lifeCollected) {
                    continue;
                }

                if (checkCollision(playerEntity, lifeCollectible)) {//remove entities that are slightly below x axis
                    soundSystem.playCollectLifeSound();
                    lifeCollectibleComponent.lifeCollected = true;
                    getEngine().removeEntity(lifeCollectible); //remove entity after all processes are done
                    GameManager.INSTANCE.increaseLives();

                }
            }
        }

//        check collision between player and score collectibles
        for (Entity playerEntity : players) {
            for (Entity scoreCollectible : scoreCollectibles) {

                ScoreCollectibleComponent scoreCollectibleComponent = Mappers.SCORE_COLLECTIBLE_COMPONENT.get(scoreCollectible);

                if (scoreCollectibleComponent.scoreCollected) {
                    continue;
                }

                if (checkCollision(playerEntity, scoreCollectible)) {//remove entities that are slightly below x axis
                    soundSystem.playCollectScoreSound();
                    scoreCollectibleComponent.scoreCollected = true;
                    getEngine().removeEntity(scoreCollectible); //remove entity after all processes are done
                    GameManager.INSTANCE.increaseScore();
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
