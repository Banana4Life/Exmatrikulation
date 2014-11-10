package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Transform;

/** todo additional authors
 * The ControlSystem moves all Entities with a DestTransform Component towards the destination
 * It uses the Family {Transform, DestTransform}
 * @author Tim Adamek
 */
public class ControlSystem extends IteratingSystem {
    private ComponentMapper<Transform> transforms;
    private ComponentMapper<DestTransform> destTransforms;

    private final int VECTOR_RANGE = 1;
    private final float MOVEMENT_SPEED = 0.2f;

    /**
     * The constructor gets the ComponentMapper for Transform and DestTransform
     */
    public ControlSystem() {
        super(Family.all(Transform.class, DestTransform.class).get());

        this.transforms = ComponentMapper.getFor(Transform.class);
        this.destTransforms = ComponentMapper.getFor(DestTransform.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Transform transform = transforms.get(entity);
        DestTransform destTransform = destTransforms.get(entity);

        if (!vectorsInRange(transform.getPosition(), destTransform.getPosition())) {
            transform.setPosition(vectorInterpolation(destTransform.getPosition(), transform.getPosition()));
        } else {
            transform.setPosition(destTransform.getPosition());
            entity.remove(DestTransform.class);
        }
        transform.setRotation(destTransform.getRotation());
    }

    private Vector3 vectorInterpolation(Vector3 destPosition, Vector3 orgPosition) {
        float x = orgPosition.x + MOVEMENT_SPEED * (destPosition.x - orgPosition.x);
        float y = orgPosition.y + MOVEMENT_SPEED * (destPosition.y - orgPosition.y);
        float z = orgPosition.z + MOVEMENT_SPEED * (destPosition.z - orgPosition.z);
        return new Vector3(x, y, z);
    }

    private boolean vectorsInRange(Vector3 vector1, Vector3 vector2) {
        if (Math.abs(vector1.x - vector2.x) < VECTOR_RANGE && Math.abs(vector1.y - vector2.y) < VECTOR_RANGE && Math.abs(vector1.z - vector2.z) < VECTOR_RANGE) {
            return true;
        }
        return false;
    }
}
