package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import de.cubeisland.games.dhbw.entity.component.Card;
import de.cubeisland.games.dhbw.entity.component.Dice;
import de.cubeisland.games.dhbw.resource.bag.Cards;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.util.ActionTuple;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Jonas Dann
 */
public class DecidingState extends GameState {

    public static final short ID = 5;

    private int maxSemester;
    private int currentSemester = 1;

    private Map<Card, Integer> cardDurationMap = new HashMap<>();

    @Override
    public short id() {
        return ID;
    }

    @Override
    public void update(StateContext context, float delta) {
        Entity dice = context.getEngine().getEntitiesFor(Family.all(Dice.class).get()).first();

        if (dice.getComponent(Dice.class).getTicks() < 1) {
            Card card = ((ReactingState) context.getStateManager().getState(ReactingState.ID)).getEvent().getComponent(Card.class);
            Set<ActionTuple> actions = card.getActions();

            boolean requirementPassed = card.getRequirement().passed(context.getCharacter(), dice.getComponent(Dice.class).getCount());
            if (requirementPassed) {
                for (ActionTuple action : actions) {
                    action.apply(context.getCharacter());
                }
            } else {
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
}
