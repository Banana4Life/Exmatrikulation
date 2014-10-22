package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.entity.component.Model;
import de.cubeisland.games.dhbw.entity.component.Transform;

public class ModelSystem extends IteratingSystem {
    private ComponentMapper<Model> models;
    private ComponentMapper<Transform> transforms;

    public ModelSystem() {
        super(Family.getFor(Model.class, Transform.class));

        this.models = ComponentMapper.getFor(Model.class);
        this.transforms = ComponentMapper.getFor(Transform.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Model model = models.get(entity);
        Transform transform = transforms.get(entity);

        model.getModelObject().setRotation(transform.getRotation());
        model.getModelObject().setPosition(transform.getPosition());
    }
}
