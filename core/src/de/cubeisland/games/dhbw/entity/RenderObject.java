package de.cubeisland.games.dhbw.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * This interface specifies an object that can be rendered on the screen.
 *
 * @author Phillip Schichtel
 * @author Jonas Dann
 */
public interface RenderObject {
    /**
     * Renders the object to the screen.
     *
     * @param game      the main game instance
     * @param cam       the camera
     * @param e         the corresponding entity
     * @param transform the position of the entity
     */
    void render(DHBWGame game, Camera cam, Entity e, Transform transform);

    /**
     * Checks whether the given screen position is inside this object.
     *
     * @param cam the camera
     * @param x   the screen x position
     * @param y   the screen y position
     * @return true if the position is inside this object, false otherwise
     */
    boolean isWithin(Camera cam, float x, float y);
}
