package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.DecidingState;

/**
 * this is the transition to the next semester
 *
 * @author Tim Adamek
 */
public class NextSemsterTransition extends StateTransition {

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        int lastSemester = ((DecidingState)context.getStateManager().getState(DecidingState.ID)).getCurrentSemester();
        ((DecidingState)context.getStateManager().getState(DecidingState.ID)).setCurrentSemester(lastSemester+1);


        //TODO show picture
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        //TODO check?
        return true;
    }

}
