package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.*;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.ReactingState;

/**
 * This class is the transition which occurs when the player lost the game.
 *
 * @author Tim Adamek
 * @author Andreas Geis
 */
public class GameLostTransition extends StateTransition {

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        DHBWGame game = context.getGame();

        Entity[] entities = game.getEngine().getEntitiesFor(Family.one(Card.class, Deck.class, CardHand.class, Dice.class,ToMenu.class, PlayerChar.class).get()).toArray(Entity.class);
        for (Entity entity : entities) {
            game.getEngine().removeEntity(entity);
        }

        // remove calkboard
        ReactingState myReactingState = (ReactingState) context.getStateManager().getState(ReactingState.ID);
        Entity calkboard = myReactingState.getCalkBoard();
        context.getEngine().removeEntity(calkboard);
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        return true;
    }

}
