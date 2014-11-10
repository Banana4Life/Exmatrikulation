package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.Transform;

/**
 * The DeckSystem updates the decks every update cycle
 * It uses the Family {Deck, Transform}
 *
 * @author Jonas Dann
 */
public class DeckSystem extends IteratingSystem {
    private ComponentMapper<Transform> transforms;
    private ComponentMapper<Deck> decks;

    /**
     * The constructor gets the ComponentMapper for Transform and Deck
     */
    public DeckSystem() {
        super(Family.all(Deck.class, Transform.class).get());

        transforms = ComponentMapper.getFor(Transform.class);
        decks = ComponentMapper.getFor(Deck.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        decks.get(entity).update(transforms.get(entity));
    }
}
