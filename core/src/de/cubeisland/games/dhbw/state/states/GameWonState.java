package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;

/**
 * This Class represents the GameWon-Screen.
 * If the player clicks on the screen he gets back to the main menu.
 *
 * @author Andreas Geis
 * @author Tim Adamek
 */
public class GameWonState extends GameState {

    public static final short ID = 7;

    private Entity gamewon;

    @Override
    public short id() {
        return ID;
    }

    @Override
    public void onEnter(StateContext context, GameState from) {
        DHBWGame game = context.getGame();
        // check which mode was played
        if(((DecidingState) context.getStateManager().getState(DecidingState.ID)).getMaxSemester() == 1) {
            gamewon = game.getEntityFactory().createImage("images/winscreenfreemode.png", new Vector3(0, 0, -280), .344f);
        } else {
            gamewon = game.getEntityFactory().createImage("images/winscreenstorymode.png", new Vector3(0, 0, -280), .344f);
        }

        context.getEngine().addEntity(gamewon);
    }

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        //check if player presses the left mouse button, if not return false
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        context.transitionTo(MainMenu.ID);
        return true;
    }

    @Override
    public void onLeave(StateContext context, GameState to) {
        context.getEngine().removeEntity(gamewon);
    }
}
