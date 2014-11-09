package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import de.cubeisland.games.dhbw.entity.component.Dice;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;

/**
 * @author Jonas Dann
 */
public class DecidingState extends GameState {

    public static final short ID = 5;

    @Override
    public short id() {
        return ID;
    }

    @Override
    public void update(StateContext context, float delta) {
        Entity dice = context.getEngine().getEntitiesFor(Family.all(Dice.class).get()).first();

        if (dice.getComponent(Dice.class).getTicks() < 1) {
            //TODO test against event
            context.transitionTo(ReactingState.ID);
        }
    }
}
