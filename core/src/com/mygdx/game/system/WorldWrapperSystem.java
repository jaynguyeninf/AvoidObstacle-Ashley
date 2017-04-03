package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SizeComponent;
import com.mygdx.game.components.WorldWrapperComponent;

/**
 * Created by Jay Nguyen on 3/30/2017.
 */

public class WorldWrapperSystem extends IteratingSystem {

    private Viewport viewport;

    private static final Family FAMILY = Family.all(
            WorldWrapperComponent.class,
            PositionComponent.class,
            SizeComponent.class
    ).get();

    public WorldWrapperSystem(Viewport viewport) {
        super(FAMILY);
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = Mappers.POSITION_COMPONENT.get(entity);
        SizeComponent sizeComponent = Mappers.SIZE_COMPONENT.get(entity);

        positionComponent.x = MathUtils.clamp(positionComponent.x, 0, viewport.getWorldWidth() - sizeComponent.width);
        positionComponent.y = MathUtils.clamp(positionComponent.y, 0, viewport.getWorldHeight());
    }
}
