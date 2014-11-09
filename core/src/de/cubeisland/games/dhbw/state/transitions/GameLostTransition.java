package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

/**
 * This class is the transition which occurs when the player lost the game.
 *
 * @author Andreas Geis
 */
public class GameLostTransition extends StateTransition {

    public static final GameLostTransition INSTANCE = new GameLostTransition();

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        DHBWGame game = context.getGame();

        game.getEngine().removeAllEntities();
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        return true;
    }

}
