package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Picked;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * The PickSystem sets the DestTransform of all picked entities to the mouse position.
 * It uses the Family {Picked, Transform}
 */
public class PickSystem extends IteratingSystem {
    private PerspectiveCamera cam;

    /**
     * The constructor sets the camera given as parameter.
     * @param camera The camera of the game.
     */
    public PickSystem(PerspectiveCamera camera) {
        super(Family.getFor(Picked.class, Transform.class));

        this.cam = camera;
    }
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        entity.add(new DestTransform(cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), cam.project(new Vector3(0, 0, -100)).z)), new Quaternion(new Vector3(0, 1, 0), 0)));
    }
}
