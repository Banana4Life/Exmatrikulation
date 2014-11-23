package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Card;
import de.cubeisland.games.dhbw.entity.component.Dice;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.ActionTuple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Jonas Dann
 * @author Tim Adamek
 */
public class DecidingState extends GameState {

    public static final short ID = 5;

    private int maxSemester;
    private int currentSemester = 1;

    private int lastDiceTick = 0;
    private Card lastEvent;
    private boolean passedLastEvent;

    private boolean overlayRemoved = true;
    private Entity overlay;
    private Entity text;

    private Map<Card, Integer> cardDurationMap = new HashMap<>();

    @Override
    public short id() {
        return ID;
    }

    @Override
    public void onEnter(StateContext context, GameState from) {
        Entity dice = context.getEngine().getEntitiesFor(Family.all(Dice.class).get()).first();
        Card card = ((ReactingState) context.getStateManager().getState(ReactingState.ID)).getEvent().getComponent(Card.class);
        passedLastEvent = card.getRequirement().passed(context.getCharacter(), dice.getComponent(Dice.class).getCount());
        lastEvent = ((ReactingState) context.getStateManager().getState(ReactingState.ID)).getEvent().getComponent(Card.class);
        String summary = generateSummary(context);
        overlay = context.getGame().getEntityFactory().createImage("images/overlay.png", new Vector3(0, 0, -50), .344f);
        context.getEngine().addEntity(overlay);
        overlayRemoved = false;
        text = context.getGame().getEntityFactory().createText(summary, context.getGame().getResources().fonts.cardFont, Color.BLACK, new Vector2(-250, 0));
        context.getEngine().addEntity(text);
    }

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        if (!overlayRemoved) {
            context.getEngine().removeEntity(overlay);
            context.getEngine().removeEntity(text);
            overlayRemoved = true;
        }
        return false;
    }

    @Override
    public void update(StateContext context, float delta) {
        if (overlayRemoved) {
            Entity dice = context.getEngine().getEntitiesFor(Family.all(Dice.class).get()).first();

            Card card = ((ReactingState) context.getStateManager().getState(ReactingState.ID)).getEvent().getComponent(Card.class);
            Set<ActionTuple> actions = card.getActions();

            //remeber the tick count for the summary
            lastDiceTick = dice.getComponent(Dice.class).getCount();

            boolean requirementPassed = card.getRequirement().passed(context.getCharacter(), dice.getComponent(Dice.class).getCount());
            if (requirementPassed) {
                for (ActionTuple action : actions) {
                    action.apply(context.getCharacter());
                }
            } else {
                if (card.equals(context.getGame().getResources().cards.eventexmatrikulator)) {
                    context.getGame().getResources().sounds.decapitation.play();
                }
                for (ActionTuple action : actions) {
                    action.unapply(context.getCharacter());
                }
            }

            for (Card playedCard : new HashSet<>(cardDurationMap.keySet())) {
                if (playedCard.getDuration() == cardDurationMap.get(playedCard)) {
                    for (ActionTuple action : playedCard.getActions()) {
                        action.unapply(context.getCharacter());
                    }
                    cardDurationMap.remove(playedCard);
                } else {
                    cardDurationMap.put(playedCard, cardDurationMap.get(playedCard) + 1);
                }
            }

            if (((ReactingState) context.getStateManager().getState(ReactingState.ID)).getCardsInEventDeck() == 0) {
                if (!requirementPassed) {
                    ((DecidingState) context.getStateManager().getState(DecidingState.ID)).setCurrentSemester(1);
                    context.transitionTo(GameLostState.ID);

                } else if (currentSemester < maxSemester) {
                    for (Card playedCard : new HashSet<>(cardDurationMap.keySet())) {
                        for (ActionTuple action : playedCard.getActions()) {
                            action.unapply(context.getCharacter());
                        }
                        cardDurationMap.remove(playedCard);
                    }
                    context.transitionTo(NextSemester.ID);
                } else {
                    ((DecidingState) context.getStateManager().getState(DecidingState.ID)).setCurrentSemester(1);
                    context.transitionTo(GameWonState.ID);
                }
            } else {
                context.transitionTo(ReactingState.ID);
            }
        }
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public int getMaxSemester() {
        return maxSemester;
    }

    public void setMaxSemester(int maxSemester) {
        this.maxSemester = maxSemester;
    }

    public Map<Card, Integer> getCardDurationMap() {
        return cardDurationMap;
    }

    public void setCardDurationMap(Map<Card, Integer> cardDurationMap) {
        this.cardDurationMap = cardDurationMap;
    }

    public int getLastDiceTick() {
        return lastDiceTick;
    }

    public void setLastDiceTick(int lastDiceTick) {
        this.lastDiceTick = lastDiceTick;
    }

    public boolean isPassedLastEvent() {
        return passedLastEvent;
    }

    public void setPassedLastEvent(boolean passedLastEvent) {
        this.passedLastEvent = passedLastEvent;
    }

    public Card getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(Card lastEvent) {
        this.lastEvent = lastEvent;
    }

    private String generateSummary(StateContext context) {
        String summary = new String();
        DecidingState state = (DecidingState) context.getStateManager().getState(DecidingState.ID);

        if (this.isPassedLastEvent()) {
            summary += "Du hast das letzte Event bestanden.\n";
        } else {
            summary += "Du hast das letzte Event nicht bestanden.\n";
        }

        summary += "Du musstest einen Wert von " + Integer.toString(this.lastEvent.getRequirement().value) + " in " + this.lastEvent.getRequirement().subject + " erreichen\n";
        summary += "und hast einen Wert von " + Integer.toString(context.getGame().getCharacter().get(this.lastEvent.getRequirement().subject) + context.getEngine().getEntitiesFor(Family.all(Dice.class).get()).first().getComponent(Dice.class).getCount()) + " erreicht.\n";
        return summary;
    }
}
