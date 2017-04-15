package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SizeComponent;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 4/14/2017.
 */

public class TouchInputSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PlayerComponent.class,
            PositionComponent.class,
            SizeComponent.class
    ).get();

    private final Viewport viewport;

    public TouchInputSystem(Viewport viewport) {
        super(FAMILY);
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent positionComponent = Mappers.POSITION_COMPONENT.get(entity);
        SizeComponent sizeComponent = Mappers.SIZE_COMPONENT.get(entity);

        Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector2 worldTouch = viewport.unproject(screenTouch.cpy());

        if (Gdx.input.isTouched() && !GameManager.INSTANCE.isGameOver()) {
            worldTouch.x = MathUtils.clamp(worldTouch.x, 0, GameConfig.WORLD_WIDTH - sizeComponent.width);
            positionComponent.x = worldTouch.x;
        }
    }
}
