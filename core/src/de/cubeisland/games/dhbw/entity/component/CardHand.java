package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.util.ActionTuple;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * CardHand saves all the cards that are on the players hand.
 *
 * @author Jonas Dann
 */
public class CardHand extends Component implements Iterable<Entity> {
    private static final int GAP = 27;

    private Vector3 destPos;
    private Quaternion destRot;
    private ArrayList<Entity> cards = new ArrayList<>();

    private int highlighted = -1;

    /**
     * Updates the card positions.
     *
     * @param transform The transform to update to.
     * @return Returns this.
     */
    public CardHand update(Transform transform) {
        for (int n = 0; n < cards.size(); n++) {
            DestTransform destTransform;
            if (n == highlighted) {
                destTransform = new DestTransform(transform.getPosition().cpy().add(n * GAP - getWidth() / 2f, 5, 15), transform.getRotation());
            } else {
                destTransform = new DestTransform(transform.getPosition().cpy().add(n * GAP - getWidth() / 2f, 0, -n / 10), transform.getRotation());
            }
            if (!cards.get(n).getComponent(Transform.class).equals(destTransform)) {
                cards.get(n).add(destTransform);
            }
        }
        return this;
    }

    /**
     * Plays the highlighted card from the hand.
     *
     * @return Returns the played card.
     */
    public Entity playCard(PlayerCharacter pc) {
        if (highlighted > -1 && highlighted < cards.size()) {
            Entity card = cards.remove(highlighted);
            card.add(new DestTransform(destPos, destRot));
            card.remove(Pickable.class);
            for (ActionTuple action : card.getComponent(Card.class).getActions()) {
                action.apply(pc);
            }
            return card;
        }
        highlighted = -1;
        return null;
    }

    /**
     * highlights the given card.
     *
     * @param card The card to highlight.
     * @return Returns this,
     */
    public boolean highlightCard(Entity card) {
        highlighted = cards.indexOf(card);
        return highlighted > -1;
    }

    /**
     * Adds a card to the hand.
     *
     * @param card The card to add.
     * @return Returns this.
     */
    public CardHand addCard(Entity card) {
        card.remove(Pickable.class);
        cards.add(card);
        return this;
    }

    /**
     * Sets the destination position of the card hand.
     *
     * @param destPos The position.
     * @return Returns this.
     */
    public CardHand setDestPos(Vector3 destPos) {
        this.destPos = destPos;
        return this;
    }

    /**
     * Sets the destination rotation of the card hand.
     *
     * @param destRot The rotation.
     * @return Returns this.
     */
    public CardHand setDestRot(Quaternion destRot) {
        this.destRot = destRot;
        return this;
    }

    private int getWidth() {
        return (cards.size() - 1) * GAP;
    }

    @Override
    public Iterator<Entity> iterator() {
        return cards.iterator();
    }
}
