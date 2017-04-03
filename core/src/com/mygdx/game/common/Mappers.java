package com.mygdx.game.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.LifeCollectibleComponent;
import com.mygdx.game.components.SizeComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.TextureComponent;

/**
 * Created by Jay Nguyen on 3/30/2017.
 */

//access component's instance everywhere
public class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS_COMPONENT = ComponentMapper.getFor(BoundsComponent.class);
    public static final ComponentMapper<MovementComponent> MOVEMENT_COMPONENT = ComponentMapper.getFor(MovementComponent.class);
    public static final ComponentMapper<PositionComponent> POSITION_COMPONENT = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<ObstacleComponent> OBSTACLE_COMPONENT = ComponentMapper.getFor(ObstacleComponent.class);
    public static final ComponentMapper<LifeCollectibleComponent> LIVES_COIN_COMPONENT = ComponentMapper.getFor(LifeCollectibleComponent.class);
    public static final ComponentMapper<PlayerComponent> PLAYER_COMPONENT = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<TextureComponent> TEXTURE_COMPONENT = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<SizeComponent> SIZE_COMPONENT = ComponentMapper.getFor(SizeComponent.class);

    private Mappers() {
    }
}
