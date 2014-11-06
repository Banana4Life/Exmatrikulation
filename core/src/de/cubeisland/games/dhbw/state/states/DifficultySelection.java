package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * Checks if the player clicks on a card and where the card belongs to.
 * This Class is the Course Selection state, in this state the player can choose
 * between three different difficulties.
 * In addition he can go back to Main Menu, Course Selection and Character Selection
 *
 * @Author Tim Adamek
 */
public class DifficultySelection extends MenuState {

    public static final short ID = 5;

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
                context.getStateManager().transitionTo(Playing.ID);
                return true;
            } else {
                if (((MainMenu) context.getStateManager().getState(MainMenu.ID)).getCardStack().contains(e)) {
                    //The player has clicked on the card Stack of MainMenu and moves back to main menu
                    pickedcard = e;
                    context.getStateManager().transitionTo(MainMenu.ID);
                } else if (((CourseSelection) context.getStateManager().getState(CourseSelection.ID)).getCardStack().contains(e)) {
                    //The player has clicked on the card Stack of courseSelection and moves back to main menu
                    pickedcard = e;
                    context.getStateManager().transitionTo(CourseSelection.ID);
                } else if (((CharacterSelection) context.getStateManager().getState(CharacterSelection.ID)).getCardStack().contains(e)) {
                    //The player has clicked on the card Stack of Character Selection and moves back to main menu
                    pickedcard = e;
                    context.getStateManager().transitionTo(CharacterSelection.ID);
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
