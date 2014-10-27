package de.cubeisland.games.dhbw.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.entity.component.Transform;

public abstract class EntityUtil {
    private EntityUtil() {}

    public static Entity getEntityAt(Engine engine, Camera camera, float screenX, float screenY) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.getFor(Render.class));
        if (entities.size() == 0) {
            return null;
        }
        Entity highestEntity = entities.get(0);
        float highestZ = highestEntity.getComponent(Transform.class).getPosition().z;
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
