package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.entity.component.*;
import de.cubeisland.games.dhbw.entity.object.DiceObject;
import de.cubeisland.games.dhbw.entity.object.ToMenuObject;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.EntityUtil;

/**
 * ReactingState is the state in the game, where the player can react to events he is facing.
 *
 * @author Tim Adamek
 * @author Jonas Dann
 */
public class ReactingState extends GameState {

    public static final short ID = 4;

    public static final int STARTCARDCOUNT = 4;

    private Entity event;
    private Entity eventDeck;
    private Entity itemDeck;
    private Entity calkBoard;

    @Override
    public void update(StateContext context, float delta) {
        super.update(context, delta);
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), Gdx.input.getX(), Gdx.input.getY());
        Entity cardHand = context.getEngine().getEntitiesFor(Family.one(CardHand.class).get()).first();
        if (e != null) {
            if (e.getComponent(Card.class) != null) {
                cardHand.getComponent(CardHand.class).highlightCard(e);
            } else if (e.getComponent(Render.class).getObject().getClass() == ToMenuObject.class) {
                ((ToMenuObject) e.getComponent(Render.class).getObject()).setHover(true);
            } else if (e.getComponent(Render.class).getObject().getClass() == DiceObject.class) {
                ((DiceObject) e.getComponent(Render.class).getObject()).setHover(true);
            } else if (e == calkBoard) {
                System.out.println("calkboard");
            }
        }
    }

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        Entity entity = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        if (entity != null) {
            if (button == Input.Buttons.LEFT) {
                Entity cardHand = context.getEngine().getEntitiesFor(Family.one(CardHand.class).get()).first();
                if (entity.getComponent(Card.class) != null && cardHand.getComponent(CardHand.class).highlightCard(entity)) {
                    context.getGame().getResources().sounds.cardflip.play();
                    Entity card = cardHand.getComponent(CardHand.class).playCard(context.getCharacter());
                    context.getEngine().removeEntity(card);
                    ((DecidingState) context.getStateManager().getState(DecidingState.ID)).getCardDurationMap().put(entity.getComponent(Card.class), 1);
                    return true;
                } else if (entity.getComponent(Dice.class) != null) {
                    context.getGame().getResources().sounds.dice.play();
                    entity.getComponent(Dice.class).setTicks(160);
                    context.transitionTo(DecidingState.ID);
                    return true;
                } else if (entity.getComponent(ToMenu.class) != null) {
                    context.getGame().getResources().sounds.homebutton.play();
                    context.transitionTo(MainMenu.ID);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public short id() {
        return ID;
    }

    /**
     * Returns the current event.
     *
     * @return Returns event.
     */
    public Entity getEvent() {
        return event;
    }

    /**
     * Sets the new current event.
     *
     * @param event The new current event.
     */
    public ReactingState setEvent(Entity event) {
        this.event = event;
        return this;
    }

    /**
     * Draws a new card to the event variable.
     *
     * @return this.
     */
    public ReactingState drawEvent() {
        this.event = eventDeck.getComponent(Deck.class).drawCard();
        return this;
    }

    public Entity getEventDeck() {
        return eventDeck;
    }

    public ReactingState setEventDeck(Entity eventDeck) {
        this.eventDeck = eventDeck;
        return this;
    }

    public Entity getItemDeck() {
        return itemDeck;
    }

    public ReactingState setItemDeck(Entity itemDeck) {
        this.itemDeck = itemDeck;
        return this;
    }

    public int getCardsInEventDeck() {
        return eventDeck.getComponent(Deck.class).getCardCount();
    }

    public Entity getCalkBoard() {
        return calkBoard;
    }

    public void setCalkBoard(Entity calkBoard) {
        this.calkBoard = calkBoard;
    }
}
