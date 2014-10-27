package de.cubeisland.games.dhbw.state.states;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateManager;

public class Paused extends GameState {

    public static final short ID = 6;

    @Override
    public short id() {
        return ID;
    }
}
