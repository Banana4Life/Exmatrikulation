package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.transitions.MergeCardsAndMoveToCorner;
import de.cubeisland.games.dhbw.util.EntityUtil;

public class CourseSelection extends GameState {

    public static final short ID = 3;

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        if (e != null) {
            //TODO choose char stat and add depending on card
            MergeCardsAndMoveToCorner.setPickedcard(e);
            MergeCardsAndMoveToCorner.removeRestCard(e);
            context.getStateManager().transitionTo(CharacterSelection.ID);
            context.getStateManager().update(1);//TODO check float value
            return true;
        }
        return false;
    }

    @Override
    public short id() {
        return ID;
    }
}
