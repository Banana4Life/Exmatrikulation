package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

/**
 * Created by Tim Adamek on 08.11.2014.
 */
public class GameWonTransition extends StateTransition {

    public static final GameWonTransition INSTANCE = new GameWonTransition();

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        return false;
    }
}
