package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.RenderObject;
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
    private boolean overlayRemoved = true;
    private Entity overlay;
    boolean firstEnter = true;//TODO set tru when game lost/won

    private Entity text;

    @Override
    public void onEnter(StateContext context, GameState from) {
        if (!firstEnter) {
            String summary = generateSummary(context);
            overlay = context.getGame().getEntityFactory().createImage("images/overlay.png", new Vector3(0, 0, -50), .344f);
            context.getEngine().addEntity(overlay);
            overlayRemoved = false;
            text = context.getGame().getEntityFactory().createText(summary, context.getGame().getResources().fonts.cardFont, new Color(0.2f, 0.05f, 0.05f, 1f), new Vector2(-250, 0)); //TODO set position
            context.getEngine().addEntity(text);
        }
        firstEnter = false;
    }

    @Override
    public void update(StateContext context, float delta) {
        if (overlayRemoved) {
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
    }

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        if (overlayRemoved) {
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
        } else {
            context.getEngine().removeEntity(overlay);
            context.getEngine().removeEntity(text);
            overlayRemoved = true;
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

    private String generateSummary(StateContext context) {
        String summary = new String();
        DecidingState state = (DecidingState) context.getStateManager().getState(DecidingState.ID);

        if (state.isPassedLastEvent()) {
            summary += "Du hast das letzte Event bestanden.\n";
        } else {
            summary += "Du hast das letzte Event nicht bestanden.\n";
        }

        summary += "Du musstest einen Wert von " + Integer.toString(state.getLastEvent().getRequirement().value) + " in " + state.getLastEvent().getRequirement().subject + " erreichen\n";
        summary += "und hast einen Wert von " + Integer.toString(context.getGame().getCharacter().get(state.getLastEvent().getRequirement().subject) + context.getEngine().getEntitiesFor(Family.all(Dice.class).get()).first().getComponent(Dice.class).getCount()) + " erreicht.\n";
        return summary;
    }

    public boolean isFirstEnter() {
        return firstEnter;
    }

    public void setFirstEnter(boolean firstEnter) {
        this.firstEnter = firstEnter;
    }
}
