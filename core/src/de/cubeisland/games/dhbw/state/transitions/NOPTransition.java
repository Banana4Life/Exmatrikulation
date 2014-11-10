package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

/**
 * todo javadoc
 */
public class NOPTransition extends StateTransition {

    public static final NOPTransition INSTANCE = new NOPTransition();

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        return true;
    }

}
