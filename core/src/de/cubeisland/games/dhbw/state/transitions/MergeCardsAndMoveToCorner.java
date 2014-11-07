package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.*;

import java.util.List;

public class MergeCardsAndMoveToCorner extends StateTransition {

    private static boolean cardsDrawn = false;

    public static final MergeCardsAndMoveToCorner INSTANCE = new MergeCardsAndMoveToCorner();

     @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        //saving static Card/CardList of current States in a local variable
        //to only need to check for setting the variables and not for the complete function
        Entity pickedCard;
        List<Entity> cardList;
        MenuState state = (MenuState) context.getStateManager().getState(origin.id());
        if (state.id() == MainMenu.ID) {
            pickedCard = state.getPickedcard();
            cardList = state.getCardStack();
        } else {
            pickedCard = state.getPickedcard();
            cardList = state.getCardStack();
        }

        //moves the cards to the top corner
        for (Entity card : cardList) {
            card.add(new DestTransform(new Vector3(75 -  40, 75, -250), new Quaternion(new Vector3(0, 0, 0), -100)));
        }
        //moves the picked card to the top of the stack
        pickedCard.add(new DestTransform(new Vector3(75 -  40, 75, -248), new Quaternion(new Vector3(0, 0, 0), -100)));
    }

   @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        //checking for state and saving to next state
        List<Entity> cardList;

        MenuState state = (MenuState) context.getStateManager().getState(destination.id());
        if (state.id() == MainMenu.ID) {
            cardList = state.getCardStack();
        } else  {
            cardList = state.getCardStack();
        }

        //draws 3 new cards and add them to the state
        if (!cardsDrawn) {
            for (int i = 0; i < 3; i++) {
                Entity card = context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).drawCard();
                cardList.add(card);
                card.add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
            }

            //cards have been drawn
            cardsDrawn = true;
            return false;
        } else {
            //Result indicates if transition is over:
            //When no card contains a DestTransform the cards are on there destination Position
            //and the transition is over
            boolean result = true;

            for (Entity card : cardList) {
                for (int i = 0; i < card.getComponents().size(); i++) {
                    if (card.getComponents().get(i).getClass() == DestTransform.class) {
                        result = false;
                    }
                }
            }
            if (result) {
                //cards can be drawn again
                cardsDrawn = false;
            }
            return result;
        }
    }

}
