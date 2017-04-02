package com.mygdx.game.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PositionComponent;

/**
 * Created by Jay Nguyen on 3/30/2017.
 */

//access component's instance everywhere
public class Mappers  {

    public static final ComponentMapper<BoundsComponent> BOUNDS_COMPONENT = ComponentMapper.getFor(BoundsComponent.class);
    public static final ComponentMapper<MovementComponent> MOVEMENT_COMPONENT = ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<PositionComponent> POSITION_COMPONENT= ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<ObstacleComponent> OBSTACLE_COMPONENT= ComponentMapper.getFor(ObstacleComponent.class);

    private Mappers(){}
}
