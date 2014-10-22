package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.entity.component.CardModel;
import de.cubeisland.games.dhbw.entity.component.Transform;

public class DecalSystem extends IteratingSystem {
    private ComponentMapper<CardModel> cardModels;
    private ComponentMapper<Transform> transforms;

    public DecalSystem() {
        super(Family.getFor(CardModel.class, Transform.class));

        this.cardModels = ComponentMapper.getFor(CardModel.class);
        this.transforms = ComponentMapper.getFor(Transform.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        CardModel cardModel = cardModels.get(entity);
        Transform transform = transforms.get(entity);

        cardModel.setRotation(transform.getRotation());
        cardModel.setPosition(transform.getPosition());
    }
}
