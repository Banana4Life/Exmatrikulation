package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import de.cubeisland.games.dhbw.entity.CardPrefab;
import de.cubeisland.games.dhbw.entity.object.CardObject;
import de.cubeisland.games.dhbw.util.ActionTuple;

import javax.swing.*;
import java.util.Map;
import java.util.Set;

/**
 * A class that holds all the card data.
 *
 * @author Jonas Dann
 */
public class Card extends Component {
    private CardPrefab.CardType type;
    private CardObject object;
    private Set<ActionTuple> actions;
    private CardPrefab.Requirement requirement;
    private int duration;
    private double rarity;

    /**
     * The standard constructor.
     */
    public Card() {
    }

    /**
     * Constructor that sets all the values.
     * @param type        The type of the card
     * @param object      The object that will be rendered the screen
     * @param actions     The actions
     * @param requirement The requirement
     * @param duration    The duration
     * @param rarity      The rarity
     */
    public Card(CardPrefab.CardType type, CardObject object, Set<ActionTuple> actions, CardPrefab.Requirement requirement, int duration, double rarity) {
        this.type = type;
        this.object = object;
        this.actions = actions;
        this.requirement = requirement;
        this.duration = duration;
        this.rarity = rarity;
    }

    /**
     * Copies the instance.
     *
     * @return Returns a copied instance.
     */
    public Card copy() {
        return new Card(type, object, actions, requirement, duration, rarity);
    }

    /**
     * Gets a CardObject with the texture.
     *
     * @return Returns a CardObject.
     */
    public CardObject getObject() {
        return this.object;
    }

    public CardPrefab.CardType getType(){
        return this.type;
    }
}
