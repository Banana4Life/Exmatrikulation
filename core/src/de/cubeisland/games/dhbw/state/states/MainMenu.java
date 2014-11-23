package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.EntityUtil;


/**
 * This Class is the Main Menu State, in this state the player can choose
 * the game mode he wants to play
 *
 * @author Tim Adamek
 * @author Phillip Schichtel
 * @author Jonas Dann
 */
public class MainMenu extends MenuState {

    public static final short ID = 2;

    @Override
    public void update(StateContext context, float delta) {
        super.update(context, delta);

        Vector3 destPos;
        for (Entity card : cards) {
            destPos = card.getComponent(Transform.class).getPosition().cpy();
            destPos.z = -110;
            card.add(new DestTransform(destPos, card.getComponent(Transform.class)));
        }
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), Gdx.input.getX(), Gdx.input.getY());
        if (e != null && cards.contains(e)) {
            destPos = e.getComponent(Transform.class).getPosition().cpy();
            destPos.z = -105;
            e.add(new DestTransform(destPos, e.getComponent(Transform.class)));
        }
    }

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        //check if player presses the left mouse button, if not return false
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        //get the entity (the card) at the mouse position
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        if (e != null && cards.contains(e)) {
            // play cardflip sound
            context.getGame().getResources().sounds.cardflip.play();
            //remember the card the player has clicked on
            pickedcard = e;

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
