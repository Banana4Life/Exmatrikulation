package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.state.GameState;

import java.util.ArrayList;
import java.util.List;

/**
 * This state is only a meta
 * to implement methods for other states
 *
 * @author Tim Adamek
 */
public class MenuState extends GameState {

    public static final short ID = 10;
    protected Entity pickedcard;
    protected List<Entity> cards = new ArrayList<>();

    /**
     * @return returns the Cards the player can choose from
     */
    public List<Entity> getCardStack() {
        return cards;
    }

    public void setCardStack(List<Entity> cards) {
        this.cards = cards;
    }

    /**
     * @return the card the layer picked
     */
    public Entity getPickedcard() {
        return pickedcard;
    }

    @Override
    public short id() {
        return ID;
    }
}
