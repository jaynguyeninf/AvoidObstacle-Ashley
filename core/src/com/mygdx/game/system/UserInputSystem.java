package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 3/30/2017.
 */

public class UserInputSystem extends IteratingSystem {
    private static final Logger log = new Logger(UserInputSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            PlayerComponent.class,
            MovementComponent.class
    ).get();

    public UserInputSystem(){
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movementComponent = Mappers.MOVEMENT_COMPONENT.get(entity);

        movementComponent.xSpeed = 0; //stop moving if not pressed

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            movementComponent.xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            movementComponent.xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }


//        movementComponent.ySpeed = 0; //stop moving if not pressed
//        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
//            movementComponent.ySpeed = GameConfig.MAX_PLAYER_X_SPEED;
//        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
//            movementComponent.ySpeed = -GameConfig.MAX_PLAYER_X_SPEED;
//        }

        log.debug("processEntity xSpeed = " +movementComponent.xSpeed);
    }
}
