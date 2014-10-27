package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.cubeisland.games.dhbw.entity.component.Camera;

public class CameraSystem extends IteratingSystem {

    private final ComponentMapper<Camera> cameras;

    public CameraSystem() {
        super(Family.getFor(Camera.class), 5);
        this.cameras = ComponentMapper.getFor(Camera.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PerspectiveCamera cam = this.cameras.get(entity).get();

        cam.update();
    }
}
