package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import de.cubeisland.games.dhbw.entity.component.Card;
import de.cubeisland.games.dhbw.entity.component.Dice;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.ActionTuple;

import java.util.Set;

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
            Card card = ((ReactingState) context.getStateManager().getState(ReactingState.ID)).getEvent().getComponent(Card.class);
            Set<ActionTuple> actions = card.getActions();

            if (card.getRequirement().passed(context.getCharacter(), dice.getComponent(Dice.class).getCount())) {
                for (ActionTuple action : actions) {
                    action.apply(context.getCharacter());
                }
            } else {
                for (ActionTuple action : actions) {
                    action.unapply(context.getCharacter());
                }
            }

            //TODO win and lose
            context.transitionTo(ReactingState.ID);
        }
    }
}
