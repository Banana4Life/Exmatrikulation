package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.entity.component.Velocity;

public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<Transform> transforms;
    private final ComponentMapper<Velocity> velocities;

    public MovementSystem(Family family) {
        super(Family.getFor(Transform.class, Velocity.class));

        this.transforms = ComponentMapper.getFor(Transform.class);
        this.velocities = ComponentMapper.getFor(Velocity.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        transforms.get(entity).move(velocities.get(entity).get().scl(deltaTime));
    }
}