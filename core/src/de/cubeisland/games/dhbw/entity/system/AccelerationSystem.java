package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.entity.component.Acceleration;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.entity.component.Velocity;

public class AccelerationSystem extends IteratingSystem {

    private final ComponentMapper<Velocity> velocities;
    private final ComponentMapper<Acceleration> accelerations;

    public AccelerationSystem(Family family) {
        super(Family.getFor(Velocity.class, Acceleration.class));

        this.velocities = ComponentMapper.getFor(Velocity.class);
        this.accelerations = ComponentMapper.getFor(Acceleration.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        velocities.get(entity).accelerate(accelerations.get(entity).get().scl(deltaTime));
    }
}
