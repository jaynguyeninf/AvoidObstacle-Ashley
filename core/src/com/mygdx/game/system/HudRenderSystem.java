package com.mygdx.game.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.configurations.GameConfig;

/**
 * Created by Jay Nguyen on 3/31/2017.
 */

public class HudRenderSystem extends EntitySystem {

    private Viewport viewport;
    private BitmapFont font;
    private SpriteBatch batch;

    private final GlyphLayout glyphLayout = new GlyphLayout();

    public HudRenderSystem(Viewport viewport, BitmapFont font, SpriteBatch batch) {
        this.batch = batch;
        this.viewport = viewport;
        this.font = font;
    }

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        draw();
        batch.end();
    }

    private void draw() {
        String livesText = "Lives: " + GameManager.INSTANCE.getLives();
        glyphLayout.setText(font, livesText);
        font.draw(batch, glyphLayout, 20, GameConfig.HUD_HEIGHT - glyphLayout.height);

        String scoreText = "Score: " + GameManager.INSTANCE.getScore();
        glyphLayout.setText(font, scoreText);
        font.draw(batch, glyphLayout, GameConfig.HUD_WIDTH - glyphLayout.width - 20, GameConfig.HUD_HEIGHT - glyphLayout.height);
    }
}
