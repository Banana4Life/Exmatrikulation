package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

/**
 * @author Jonas Dann
 */
public class NextEventTransition extends StateTransition {
    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        //TODO draw new event card and delete the old one
        return true;
    }
}
