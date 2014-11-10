package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.character.PlayerCharacter;
import de.cubeisland.games.dhbw.entity.component.Card;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.ActionTuple;
import de.cubeisland.games.dhbw.util.EntityUtil;


/**
 * This Class is the Course Selection state, in this state the player can choose
 * between three different course of studies.
 * In addition he can go back to Main Menu and CourseSelection.
 *
 * @author Tim Adamek
 * @author Jonas Dann
 */
public class CourseSelection extends MenuState {

    public static final short ID = 3;

    @Override
    public void update(StateContext context, float delta) {
        super.update(context, delta);
        Vector3 destPos;

        Entity pickedCard = ((MenuState) context.getStateManager().getState(MainMenu.ID)).getPickedcard();
        destPos = pickedCard.getComponent(Transform.class).getPosition().cpy();
        destPos.z = -199;
        pickedCard.add(new DestTransform(destPos, pickedCard.getComponent(Transform.class)));

        for (Entity card : cards) {
            destPos = card.getComponent(Transform.class).getPosition().cpy();
            destPos.z = -110;
            card.add(new DestTransform(destPos, card.getComponent(Transform.class)));
        }
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), Gdx.input.getX(), Gdx.input.getY());
        if (e != null) {
            if (cards.contains(e)) {
                destPos = e.getComponent(Transform.class).getPosition().cpy();
                destPos.z = -105;
                e.add(new DestTransform(destPos, e.getComponent(Transform.class)));
            } else if (e == pickedCard) {
                destPos = e.getComponent(Transform.class).getPosition().cpy();
                destPos.z = -196;
                e.add(new DestTransform(destPos, e.getComponent(Transform.class)));
            }
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

        if (e != null) {
            if (cards.contains(e)) {
                //the player has clicked one of the three cards
                // play cardflip sound
                context.getGame().getResources().sounds.cardflip.play();

                final PlayerCharacter c = context.getGame().getCharacter();
                for (ActionTuple action : e.getComponent(Card.class).getActions()) {
                    action.apply(c);
                }

                //remember the card the Player has clicked on
                pickedcard = e;
                context.transitionTo(ReactingState.ID);
                return true;
            } else {
                //The player has clicked on the card Stack of MainMenu and moves back to main menu
                if (((MainMenu) context.getStateManager().getState(MainMenu.ID)).getCardStack().contains(e)) {
                    // play cardflip sound
                    context.getGame().getResources().sounds.cardflip.play();
                    pickedcard = e;
                    context.transitionTo(MainMenu.ID);
                }
            }
        }
        return false;
    }

    @Override
    public boolean keyDown(StateContext context, int keycode) {
        if (keycode == Input.Keys.BACKSPACE || keycode == Input.Keys.ESCAPE) {
            context.getGame().getResources().sounds.cardflip.play();
            context.transitionTo(MainMenu.ID);
            return true;
        }
        return false;
    }

    @Override
    public short id() {
        return ID;
    }
}
