package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Deck is a collection of cards rendered as card deck.
 *
 * @author Jonas Dann
 */
public class Deck extends Component implements Iterable<Entity> {
    private Vector3 destPos;
    private Quaternion destRot;
    private ArrayList<Entity> cards = new ArrayList<>();

    /**
     * Updates the Transforms of all the cards in the Deck to match the transform.
     *
     * @param transform Transform to update the cards with.
     * @return Returns this.
     */
    public Deck update(Transform transform) {
        for (int n = 0; n < cards.size(); n++) {
            DestTransform destTransform = new DestTransform(transform.getPosition().cpy().add(0, 0, Math.max(-n, -6)), transform.getRotation());
            if (!cards.get(n).getComponent(Transform.class).equals(destTransform)) {
                cards.get(n).add(destTransform);
            }
        }
        return this;
    }

    /**
     * Draws a card from the Deck and returns it.
     * Removes the card from the Deck if there are still cards, sets the DestTransform of the drawn card to the destPos
     * and destRot of the Deck and adds Pickable to the card.
     *
     * @return Returns the card if there was a card in the Deck. Otherwise return null.
     */
    public Entity drawCard() {
        if (cards.size() > 0) {
            Entity card = cards.remove(0);
            card.add(new DestTransform(destPos, destRot));
            card.add(new Pickable());
            return card;
        }
        return null;
    }

    /**
     * Removes Pickable from the card and adds it to the deck.
     *
     * @param card Card to add to the deck.
     * @return Returns this.
     */
    public Deck addCard(Entity card) {
        card.remove(Pickable.class);
        cards.add(card);
        return this;
    }

    /**
     * Sets the destination position of the Deck that is used when a card is drawn.
     *
     * @param destPos The new destination position.
     * @return Returns this.
     */
    public Deck setDestPos(Vector3 destPos) {
        this.destPos = destPos;
        return this;
    }

    /**
     * Sets the destination rotation of the Deck that is used when a card is drawn.
     *
     * @param destRot The new destination rotation.
     * @return Return this.
     */
    public Deck setDestRot(Quaternion destRot) {
        this.destRot = destRot;
        return this;
    }

    /**
     * Puts a card on top of the deck.
     *
     * @param card The new card to put on top.
     * @return Returns this.
     */
    public Deck putCardOnTop(Entity card) {
        card.remove(Pickable.class);
        cards.add(0, card);
        return this;
    }

    @Override
    public Iterator<Entity> iterator() {
        return cards.iterator();
    }
}
