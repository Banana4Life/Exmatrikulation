package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.*;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 * @author Jonas Dann
 */
public class ReactingState extends GameState {

    public static final short ID = 4;

    public static final int STARTCARDCOUNT = 3;

    private Entity event;

    @Override
    public void update(StateContext context, float delta) {
        super.update(context, delta);

        Entity card = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), Gdx.input.getX(), Gdx.input.getY());
        Entity cardHand = context.getEngine().getEntitiesFor(Family.one(CardHand.class).get()).first();

        cardHand.getComponent(CardHand.class).highlightCard(card);
    }

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        Entity entity = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);

        if (entity != null && button == Input.Buttons.LEFT) {
            Entity cardHand = context.getEngine().getEntitiesFor(Family.one(CardHand.class).get()).first();
            if (entity.getComponent(Card.class) != null && cardHand.getComponent(CardHand.class).highlightCard(entity)) {
                cardHand.getComponent(CardHand.class).playCard();
                //TODO apply card to character
                return true;
            } else {
                entity.getComponent(Dice.class).setTicks(60);
                context.transitionTo(DecidingState.ID);
                return true;
            }
        }

        return false;
    }

    @Override
    public short id() {
        return ID;
    }

    public Entity getEvent() {
        return event;
    }

    public void setEvent(Entity event) {
        this.event = event;
    }
}
