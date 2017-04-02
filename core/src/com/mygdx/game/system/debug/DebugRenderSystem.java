package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.BoundsComponent;

/**
 * Created by Jay Nguyen on 3/30/2017.
 */

public class DebugRenderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(BoundsComponent.class).get();

    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    public DebugRenderSystem(Viewport viewport, ShapeRenderer shapeRenderer) {
        super(FAMILY);
        this.viewport = viewport;
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void update(float deltaTime) {

        Color oldColor = shapeRenderer.getColor().cpy();//original color

        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        //call processEntity here because there's no need to call begin() and end() for every entity
        super.update(deltaTime);//this will loops through processEntity();

        shapeRenderer.end();
        shapeRenderer.setColor(oldColor);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent boundsComponent = Mappers.BOUNDS_COMPONENT.get(entity);
        shapeRenderer.circle(boundsComponent.bounds.x, boundsComponent.bounds.y, boundsComponent.bounds.radius, 30);

    }
}
