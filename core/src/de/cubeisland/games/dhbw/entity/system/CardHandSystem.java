package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.entity.component.CardHand;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * The CardHandSystem updates the card hands every update cycle
 * It uses the Family {CardHand, Transform}
 */
public class CardHandSystem extends IteratingSystem {
    private ComponentMapper<Transform> transforms;
    private ComponentMapper<CardHand> cardhands;

    /**
     * The constructor gets the ComponentMapper for Transform and Deck
     */
    public CardHandSystem() {
        super(Family.all(CardHand.class, Transform.class).get());

        transforms = ComponentMapper.getFor(Transform.class);
        cardhands = ComponentMapper.getFor(CardHand.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        cardhands.get(entity).update(transforms.get(entity));
    }
}
