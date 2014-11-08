package de.cubeisland.games.dhbw.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * This class hols static helper methods to work with entities.
 *
 * @author Phillip Schichtel
 */
public abstract class EntityUtil {
    private EntityUtil() {
    }

    /**
     * This method checks whether there is an entity at a given screen location.
     *
     * @param engine  the entity engine
     * @param camera  the camera for projections
     * @param screenX the x position on the screen
     * @param screenY the y position on the screen
     * @return the entity at the given location or null
     */
    @Nullable
    public static Entity getEntityAt(Engine engine, Camera camera, float screenX, float screenY) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(Render.class).get());
        if (entities.size() == 0) {
            return null;
        }
        Entity highestEntity = null;
        float highestZ = -Float.MAX_VALUE;
        for (int i = 1; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            float z = e.getComponent(Transform.class).getPosition().z;
            if (e.getComponent(Render.class).getObject().isWithin(camera, screenX, Gdx.graphics.getHeight() - screenY) && z > highestZ) {
                highestEntity = e;
                highestZ = z;
            }
        }
        return highestEntity;
    }
}
