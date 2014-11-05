package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.transitions.MergeCardsAndMoveToCorner;
import de.cubeisland.games.dhbw.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends MenuState {

    //TODO for all states: static vars not needed, value of objects is kept

    public static final short ID = 2;

    /**
     * Checks if the player clicks on a card and where the card bellongs to.
     * If the card is one of the 3 cars the player can choose from this card is chosen and the transition to CourseSelection is started.
     * @Author Tim Adamek
     */
    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        if (e != null && cards.contains(e)) {
            //Player has clicked on a card
            //TODO apply cards
            pickedcard=e;
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
