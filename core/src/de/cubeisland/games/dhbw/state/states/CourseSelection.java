package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.transitions.BackInMenusTransition;
import de.cubeisland.games.dhbw.state.transitions.MergeCardsAndMoveToCorner;
import de.cubeisland.games.dhbw.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

public class CourseSelection extends GameState {

    public static final short ID = 3;

    private static Entity pickedcard;
    private static List<Entity> cards = new ArrayList<>();

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        if (e != null) {
            if (cards.contains(e)) {
                //Player has clicked on a card
                //TODO choose char stat and add depending on card
                pickedcard = e;
                MergeCardsAndMoveToCorner.fromState=ID;
                MergeCardsAndMoveToCorner.toState=CharacterSelection.ID;
                context.getStateManager().transitionTo(CharacterSelection.ID);
                return true;
            }else {
                if (MainMenu.getCardStack().contains(e)) {
                    pickedcard = e;
                    BackInMenusTransition.fromState=ID;
                    BackInMenusTransition.toState=MainMenu.ID;
                    context.getStateManager().transitionTo(MainMenu.ID);
                }
            }
        }
        return false;
    }

    public static List<Entity> getCardStack() {
        return cards;
    }

    public static Entity getPickedcard() {
        return pickedcard;
    }

    @Override
    public short id() {
        return ID;
    }
}
