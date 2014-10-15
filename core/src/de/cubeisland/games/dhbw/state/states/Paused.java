package de.cubeisland.games.dhbw.state.states;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateManager;

public class Paused implements GameState {

    public static final short ID = 6;

    @Override
    public short id() {
        return ID;
    }

    @Override
    public void onEnter(StateManager manager, GameState from) {

    }

    @Override
    public void onLeave(StateManager manager, GameState to) {

    }

    @Override
    public void update(StateManager manager, float delta) {

    }
}
