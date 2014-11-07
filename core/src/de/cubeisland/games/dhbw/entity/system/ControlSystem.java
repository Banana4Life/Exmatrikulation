package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Transform;

import java.util.HashMap;
import java.util.Map;

/**
 * The ControlSystem moves all Entities with a DestTransform Component towards the destination
 * It uses the Family {Transform, DestTransform}
 */
public class ControlSystem extends IteratingSystem {
    private ComponentMapper<Transform> transforms;
    private ComponentMapper<DestTransform> destTransforms;

   private final int VECTOR_RANGE = 1;
    private final float QUATERNION_RANGE = 0.001f;
    private final float MOVEMENT_SPEED = 0.1f;
    private final float ROTATION_SPEED = 0.05f;

    /**
     * The constructor gets the ComponentMapper for Transform and DestTransform
     */
    public ControlSystem() {
        super(Family.all(Transform.class, DestTransform.class).get());

        this.transforms = ComponentMapper.getFor(Transform.class);
        this.destTransforms = ComponentMapper.getFor(DestTransform.class);
    }


    Map<Entity, Vector3> orgPositionMap = new HashMap<>();
    Map<Entity, Float> iterationMap = new HashMap<>();


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Transform transform = transforms.get(entity);
        DestTransform destTransform = destTransforms.get(entity);

        if (!orgPositionMap.containsKey(entity)) {
            orgPositionMap.put(entity, transform.getPosition());
        }

        if (!vectorsInRange(transform.getPosition(), destTransform.getPosition()) || !quaternionsInRange(transform.getRotation(), destTransform.getRotation())) {
            transform.setPosition(vectorInterpolation(destTransform.getPosition(), transform.getPosition()));
            transform.setRotation(quaternionInterpolation(destTransform.getRotation(), transform.getRotation()));
        } else {
            transform.setPosition(destTransform.getPosition());
            transform.setRotation(destTransform.getRotation());
            entity.remove(DestTransform.class);
        }
    }

    private Vector3 vectorInterpolation(Vector3 destPosition, Vector3 orgPosition) {
        float x = orgPosition.x + MOVEMENT_SPEED * (destPosition.x - orgPosition.x);
        float y = orgPosition.y + MOVEMENT_SPEED * (destPosition.y - orgPosition.y);
        float z = orgPosition.z + MOVEMENT_SPEED * (destPosition.z - orgPosition.z);
        return new Vector3(x, y, z);
    }

    private Quaternion quaternionInterpolation(Quaternion destRotation, Quaternion orgRotation) {
        float x = destRotation.x;
        float y = destRotation.y;
        float z = destRotation.z;
        float w = orgRotation.w + ROTATION_SPEED * (destRotation.w - orgRotation.w);
        return new Quaternion(x, y, z, w);
    }

    private boolean quaternionsInRange(Quaternion quaternion1, Quaternion quaternion2) {
        if (Math.abs(quaternion1.w - quaternion2.w) < QUATERNION_RANGE) {
            return true;
        }
        return false;
    }

    private boolean vectorsInRange(Vector3 vector1, Vector3 vector2) {
        if (Math.abs(vector1.x - vector2.x) < VECTOR_RANGE && Math.abs(vector1.y - vector2.y) < VECTOR_RANGE && Math.abs(vector1.z - vector2.z) < VECTOR_RANGE) {
            return true;
        }
        return false;
    }
}
