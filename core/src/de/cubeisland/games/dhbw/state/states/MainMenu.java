package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.transitions.MergeCardsAndMoveToCorner;
import de.cubeisland.games.dhbw.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends GameState {

    //TODO for all states: static vars not needed, value of objects is kept

    public static final short ID = 2;

    private static Entity pickedcard;
    private static List<Entity> cards= new ArrayList<>();

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
            MergeCardsAndMoveToCorner.fromState=ID;
            MergeCardsAndMoveToCorner.toState=CourseSelection.ID;
            context.transitionTo(CourseSelection.ID);
            return true;
        }
        return false;
    }

    public static List<Entity> getCardStack(){
        return cards;
    }

    public static Entity getPickedcard(){
        return pickedcard;
    }

    @Override
    public short id() {
        return ID;
    }
}
