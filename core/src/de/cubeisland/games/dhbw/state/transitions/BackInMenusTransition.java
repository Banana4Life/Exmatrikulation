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

/**
 * This class is the transition between the menus when going
 * back to a former state.
 * @author Tim Adamek
 */
public class BackInMenusTransition extends StateTransition {

    public static final BackInMenusTransition INSTANCE = new BackInMenusTransition();

    private static boolean cardsMoving = false;

    //TODO test if right cards are moved

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        //state saves the origin state and casts it to menu state,
        //this allows using the underlying menu methods from the original states
        if (destination.id() == MainMenu.ID) {
            //there is no remaining cardStack
            MergeCardsAndMoveToCorner.setStackCount(0);
            if (origin.id() == CourseSelection.ID) {
                putCardsInDeck(CourseSelection.ID, context);
            } else if (origin.id() == CharacterSelection.ID) {
                putCardsInDeck(CharacterSelection.ID, context);
                putCardsInDeck(CourseSelection.ID, context);
            } else {
                putCardsInDeck(DifficultySelection.ID, context);
                putCardsInDeck(CharacterSelection.ID, context);
                putCardsInDeck(CourseSelection.ID, context);
            }
        } else if (destination.id() == CourseSelection.ID) {
            //there is one remaining cardStack
            MergeCardsAndMoveToCorner.setStackCount(1);
            if (origin.id() == CharacterSelection.ID) {
                putCardsInDeck(CharacterSelection.ID, context);
            } else {
                putCardsInDeck(DifficultySelection.ID, context);
                putCardsInDeck(CharacterSelection.ID, context);
            }
        } else {//Transition to characterSelection from DifficultySelection
            //there are two remaining cardStack
            MergeCardsAndMoveToCorner.setStackCount(2);
            putCardsInDeck(DifficultySelection.ID, context);
        }
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        MenuState state = (MenuState) destination;
        if (!cardsMoving) {
            if (destination.id() == MainMenu.ID) {
                for (int i = 0; i < state.getCardStack().size(); i++) {
                    state.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
                }
            } else if (destination.id() == CourseSelection.ID) {
                for (int i = 0; i < state.getCardStack().size(); i++) {
                    state.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
                }
            } else {
                //To CharacterSelection from DifficultySelection
                for (int i = 0; i < state.getCardStack().size(); i++) {
                    state.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
                }
            }
            cardsMoving = true;
            return false;
        } else {
            boolean result;
            //check in the cardStack -depending on the gameState- if one card contains a DestTransform
            //if this is the case the cards are still moving and the transition is not over
            if (destination.id() == MainMenu.ID) {
                result = checkForDestTransform(state.getCardStack());
            } else if (destination.id() == CourseSelection.ID) {
                result = checkForDestTransform(state.getCardStack());
            } else { //Transition to CharacterSelection from DifficultySelection
                result = checkForDestTransform(state.getCardStack());
            }
            if (result) {
                cardsMoving = false;
            }
            return result;
        }
    }


    private void putCardsInDeck(short stateID, StateContext context) {
        //the order of usage matters,
        //because the cards must be in the right order in the deck

        //takes the CardStack for thr given stateID from the stateManager
        //and puts every card back to the top of the deck
        List<Entity> cards = ((MenuState) context.getStateManager().getState(stateID)).getCardStack();
        while (cards.size() > 0) {
            //first card in list is put back to deck
            context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(cards.get(0));
            //card is deleted from cardStack
            cards.remove(0);
        }
    }

    /**
     * Checks if one Entity in the cards is moving
     * @param cards the list of Entity which shall be checked
     * @return true if no card moves else false
     */
    private boolean checkForDestTransform(List<Entity> cards) {

        for (Entity card : cards) {
            for (int i = 0; i < card.getComponents().size(); i++) {
                if (card.getComponents().get(i).getClass() == DestTransform.class) {
                  return false;
                }
            }
        }
        return true;
    }
}
