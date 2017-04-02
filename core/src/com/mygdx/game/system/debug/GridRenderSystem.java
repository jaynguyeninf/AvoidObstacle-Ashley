package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.MyViewportUtil;

/**
 * Created by Jay Nguyen on 3/29/2017.
 */

//Draw debug grids
public class GridRenderSystem extends EntitySystem {

    private final Viewport viewport;
    private final ShapeRenderer shapeRenderer;

    public GridRenderSystem(Viewport viewport, ShapeRenderer shapeRenderer) {
        this.viewport = viewport;
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        MyViewportUtil.drawGrid(viewport, shapeRenderer);
    }
}
