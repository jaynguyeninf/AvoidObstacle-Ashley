package com.mygdx.game.entities;

import com.mygdx.game.configurations.GameConfig;


/**
 * Created by Vu on 1/29/2017.
 */

public class Player extends GameObjectBase {


    public Player(){
        //calls constructor from super class
        super(GameConfig.PLAYER_BOUNDS_RADIUS);
        setSize(GameConfig.PLAYER_BOUNDS_DIMENSION, GameConfig.PLAYER_BOUNDS_DIMENSION);
    }

}
