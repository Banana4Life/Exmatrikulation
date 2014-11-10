package de.cubeisland.games.dhbw.state.states;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;

/**
 * Created by Tim Adamek on 10.11.2014.
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
