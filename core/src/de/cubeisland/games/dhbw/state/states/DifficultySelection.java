package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

public class DifficultySelection extends MenuState {

    public static final short ID = 5;

   /**
   * Checks if the player clicks on a card and where the card bellongs to.
   * If the card is one of the 3 cars the player can choose from this card is chosen and the transition to CharacterSelection is started.
   * If the card is in the CardStack of MainMenu the transition to MainMenu is started.
   * If the card is in the CardStack of CourseSelection the transition to CourseSelection is started.
   * If the card is in the CardStack of CharacterSelection the transition to CharacterSelection is started.
   *
   * @Author Tim Adamek
   */
    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        if (e != null) {
            if (cards.contains(e)) {
                //Player has clicked on a card
                pickedcard = e;
                context.getStateManager().transitionTo(Playing.ID);
                return true;
            } else {
                if (((MainMenu) context.getStateManager().getState(MainMenu.ID)).getCardStack().contains(e)) {
                    pickedcard = e;
                    context.getStateManager().transitionTo(MainMenu.ID);
                } else if (((CourseSelection) context.getStateManager().getState(CourseSelection.ID)).getCardStack().contains(e)) {
                    pickedcard = e;
                    context.getStateManager().transitionTo(CourseSelection.ID);
                } else if (((CharacterSelection) context.getStateManager().getState(CharacterSelection.ID)).getCardStack().contains(e)) {
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
