package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.state.StateManager;
import de.cubeisland.games.dhbw.state.StateTransition;

public class DummyTransition implements StateTransition {

    public static final DummyTransition INSTANCE = new DummyTransition();

    @Override
    public boolean transition(StateManager manager, float delta) {
        return true;
    }
}
