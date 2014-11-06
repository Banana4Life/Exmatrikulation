package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.transitions.MergeCardsAndMoveToCorner;
import de.cubeisland.games.dhbw.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This Class is the Main Menu State, in this state the player can choose
 * the game mode he wants to play
 * @Author Tim Adamek
 */
public class MainMenu extends MenuState {

    public static final short ID = 2;

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        //check if player presses the left mouse button, if not return false
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        //get the entity (the card) at the mouse position
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        if (e != null && cards.contains(e)) {
            //remember the card the player has clicked on
            pickedcard=e;

            //start the transition to CourseSelection
            context.transitionTo(CourseSelection.ID);
            return true;
        }
        return false;
    }

    @Override
    public short id() {
        return ID;
    }
}
