package de.cubeisland.games.dhbw.state.states;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;

/**
 * this is the state between the end of one semester and
 * the beginning of the new semester
 * @author Tim Adamek
 */
public class NextSemester extends GameState {
    public static final short ID = 6;

    //TODO do something


    @Override
    public void onEnter(StateContext context, GameState from) {


        //TODO do something

        context.transitionTo(ReactingState.ID);
    }

    @Override
    public short id() {
        return ID;
    }
}
