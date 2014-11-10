package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.ReactingState;

/**
 * @author Jonas Dann
 */
public class NextEventTransition extends StateTransition {
    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        if (ReactingState.class == destination.getClass()) {
            context.getEngine().removeEntity(((ReactingState) destination).getEvent());
            ((ReactingState) destination).drawEvent();
        }
        return true;
    }
}
