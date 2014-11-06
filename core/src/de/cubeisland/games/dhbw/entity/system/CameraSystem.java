package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.cubeisland.games.dhbw.entity.component.Camera;

/**
 * The CameraSystem updates the camera every update cycle
 * It uses the Family {Camera}
 */
public class CameraSystem extends IteratingSystem {

    private final ComponentMapper<Camera> cameras;

    /**
     * The constructor gets the ComponentMapper for Camera
     */
    public CameraSystem() {
        super(Family.all(Camera.class).get(), 5);
        this.cameras = ComponentMapper.getFor(Camera.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PerspectiveCamera cam = this.cameras.get(entity).get();

        cam.update();
    }
}
