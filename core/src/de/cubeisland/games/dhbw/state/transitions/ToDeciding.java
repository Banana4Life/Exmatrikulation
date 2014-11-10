package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.DecidingState;

/**
 * The transition to deciding state
 * @author Tim Adamek
 */
public class ToDeciding extends StateTransition {

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {

    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        return true;
    }
}
