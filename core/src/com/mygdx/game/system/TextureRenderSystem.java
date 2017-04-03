package com.mygdx.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.components.SizeComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.TextureComponent;

/**
 * Created by Jay Nguyen on 4/2/2017.
 */

public class TextureRenderSystem extends EntitySystem {

    private static final Family FAMILY = Family.all(
            TextureComponent.class,
            SizeComponent.class,
            PositionComponent.class
    ).get();

    private final Viewport viewport;
    private final SpriteBatch batch;
    private Array<Entity> renderQueue = new Array<Entity>();

    public TextureRenderSystem(Viewport viewport, SpriteBatch batch){
        this.viewport = viewport;
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY);
        renderQueue.addAll(entities.toArray());

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        draw();

        batch.end();

        renderQueue.clear(); //clear() or otherwise will get NullPointerException
    }

    private void draw(){
        for(Entity entity : renderQueue){
            TextureComponent textureComponent = Mappers.TEXTURE_COMPONENT.get(entity);
            SizeComponent sizeComponent = Mappers.SIZE_COMPONENT.get(entity);
            PositionComponent positionComponent = Mappers.POSITION_COMPONENT.get(entity);

            batch.draw(textureComponent.textureRegion,
                    positionComponent.x, positionComponent.y,
                    sizeComponent.width, sizeComponent.height);

        }
    }
}
