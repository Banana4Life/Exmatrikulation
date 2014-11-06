package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.EntityUtil;


/**
 * This Class is the Course Selection state, in this state the player can choose
 * between three different course of studies.
 * In addition he can go back to Main Menu and CourseSelection.
 *
 * @Author Tim Adamek
 */
public class CourseSelection extends MenuState {

    public static final short ID = 3;

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        //check if player presses the left mouse button, if not return false
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        //get the entity (the card) at the mouse position
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        if (e != null) {
            if (cards.contains(e)) {
                //the player has clicked one of the three cards

                //remember the card the Player has clicked on
                pickedcard = e;
                //start the transition to CharacterSelection
                context.getStateManager().transitionTo(CharacterSelection.ID);
                return true;
            } else {
                //The player has clicked on the card Stack of MainMenu and moves back to main menu
                if (((MainMenu) context.getStateManager().getState(MainMenu.ID)).getCardStack().contains(e)) {
                    pickedcard = e;
                    context.getStateManager().transitionTo(MainMenu.ID);
                }
            }
        }
        return false;
    }

    @Override
    public short id() {
        return ID;
    }
}
