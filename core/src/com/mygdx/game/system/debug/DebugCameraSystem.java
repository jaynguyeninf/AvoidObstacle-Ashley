package com.mygdx.game.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.utilities.debug.DebugCameraController;

/**
 * Created by Jay Nguyen on 3/29/2017.
 */

public class DebugCameraSystem extends EntitySystem {

    private static final Logger log = new Logger(DebugCameraSystem.class.getName(), Logger.DEBUG);
    private static final DebugCameraController DEBUG_CAMERA_SYSTEM = new DebugCameraController();

    private final OrthographicCamera camera;

    public DebugCameraSystem(OrthographicCamera camera, float startX, float startY) {
        this.camera = camera;
        DEBUG_CAMERA_SYSTEM.setStartPosition(startX,startY);
    }

    @Override
    public void update(float deltaTime) {
        log.debug("update()");
        DEBUG_CAMERA_SYSTEM.handleInputDebug(deltaTime);
        DEBUG_CAMERA_SYSTEM.applyTo(camera);
    }
}
