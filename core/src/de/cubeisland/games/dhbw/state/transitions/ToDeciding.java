package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import de.cubeisland.games.dhbw.entity.component.Dice;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

/**
 * The transition to deciding state
 *
 * @author Tim Adamek
 */
public class ToDeciding extends StateTransition {

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {

    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        Entity dice = context.getEngine().getEntitiesFor(Family.all(Dice.class).get()).first();

        return dice.getComponent(Dice.class).getTicks() < 1;
    }
}
