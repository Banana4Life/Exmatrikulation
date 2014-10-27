package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateManager;
import de.cubeisland.games.dhbw.state.StateTransition;

public class MergeCardsAndMoveToCorner extends StateTransition {
    @Override
    public boolean transition(StateContext context, float delta) {
        return true;
    }
}
