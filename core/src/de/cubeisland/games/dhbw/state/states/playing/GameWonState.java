package de.cubeisland.games.dhbw.state.states.playing;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

/**
 * Created by Tim Adamek on 08.11.2014.
 */
public class GameWonState extends GameState {

    public static final short ID =4;

    @Override
    public short id() {
        return ID;
    }
}
