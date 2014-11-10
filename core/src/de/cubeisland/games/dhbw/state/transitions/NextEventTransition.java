package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import de.cubeisland.games.dhbw.entity.component.CardHand;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.ReactingState;

/**
 * @author Jonas Dann
 * @author Andreas Geis
 */
public class NextEventTransition extends StateTransition {
    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        if (ReactingState.class == destination.getClass()) {
            context.getEngine().removeEntity(((ReactingState) destination).getEvent());
            ((ReactingState) destination).drawEvent();

            // make the player draw a new item-card
            Entity myHandEntity = context.getEngine().getEntitiesFor(Family.one(CardHand.class).get()).first();
            CardHand myHand = myHandEntity.getComponent(CardHand.class);
            // only draw a card if the player holds less than 6 cards
            if (myHand.getHandSize() < 6) {
                context.getGame().getResources().sounds.cardflip.play();
                ReactingState myReactingState = (ReactingState) context.getStateManager().getState(ReactingState.ID);
                Entity itemDeck = myReactingState.getItemDeck();
                Entity entity = itemDeck.getComponent(Deck.class).drawCard();
                myHand.addCard(entity);
            }
        }
        return true;
    }
}
