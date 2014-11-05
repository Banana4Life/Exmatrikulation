package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * The ControlSystem moves all Entities with a DestTransform Component towards the destination
 * It uses the Family {Transform, DestTransform}
 */
public class ControlSystem extends IteratingSystem {
    private ComponentMapper<Transform> transforms;
    private ComponentMapper<DestTransform> destTransforms;

    /**
     * The constructor gets the ComponentMapper for Transform and DestTransform
     */
    public ControlSystem() {
        super(Family.getFor(Transform.class, DestTransform.class));

        this.transforms = ComponentMapper.getFor(Transform.class);
        this.destTransforms = ComponentMapper.getFor(DestTransform.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Transform transform = transforms.get(entity);
        DestTransform destTransform = destTransforms.get(entity);

        transform.setPosition(destTransform.getPosition());
        transform.setRotation(destTransform.getRotation());

        entity.remove(DestTransform.class);
    }
}
