package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.state.GameState;

import java.util.ArrayList;
import java.util.List;

public class DifficultySelection extends GameState {

    public static final short ID = 5;
    private static Entity pickedcard;
    private static List<Entity> cards = new ArrayList<>();

    public static List<Entity> getCardStack() {
        return cards;
    }

    public static Entity getPickedcard() {
        return pickedcard;
    }

    @Override
    public short id() {
        return ID;
    }
}
