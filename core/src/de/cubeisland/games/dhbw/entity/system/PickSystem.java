package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Picked;
import de.cubeisland.games.dhbw.entity.component.Transform;

public class PickSystem extends IteratingSystem {
    private ComponentMapper<Transform> transforms;

    public PickSystem() {
        super(Family.getFor(Picked.class, Transform.class));

        transforms = ComponentMapper.getFor(Transform.class);
    }
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        transforms.get(entity).setPosition(new Vector3(Gdx.input.getX(), Gdx.input.getY(), -95));
    }
}
