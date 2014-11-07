package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.state.GameState;

import java.util.ArrayList;
import java.util.List;

/**
 * This state is only a meta
 * to implement methods for other states
 */
public class MenuState extends GameState {

    protected Entity pickedcard;
    protected List<Entity> cards = new ArrayList<>();

    public static final short ID = 6;

    public List<Entity> getCardStack() {
        return cards;
    }

    public Entity getPickedcard() {
        return pickedcard;
    }

    @Override
    public short id() {
        return ID;
    }
}
