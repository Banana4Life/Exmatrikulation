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
 *
 * @author Tim Adamek
 */
public class BackInMenusTransition extends StateTransition {

    public static final BackInMenusTransition INSTANCE = new BackInMenusTransition();

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        // when going back to a former state all cards
        // from states after the the destination state must be moved back to the deck
        // in addition the cards from the destination are moved tothe selection position

        MenuState state = (MenuState) destination;
        if (destination.id() == MainMenu.ID) {
            //The cards from the Main Menu are moved back to the selection position
            for (int i = 0; i < state.getCardStack().size(); i++) {
                state.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
            }

            //there is no remaining card stack so the Stack count has to be set to 0
            MergeCardsAndMoveToCorner.setStackCount(0);
            if (origin.id() == CourseSelection.ID) {
                //Transition to Main Menu from Course Selection
                putCardsInDeck(CourseSelection.ID, context);
            } else if (origin.id() == CharacterSelection.ID) {
                //Transition to Main Menu from Character Selection
                putCardsInDeck(CharacterSelection.ID, context);
                putCardsInDeck(CourseSelection.ID, context);
            } else {
                //Transition to Main Menu from Difficulty Selection
                putCardsInDeck(DifficultySelection.ID, context);
                putCardsInDeck(CharacterSelection.ID, context);
                putCardsInDeck(CourseSelection.ID, context);
            }
        } else if (destination.id() == CourseSelection.ID) {
            //The cards from the Course Selection are moved back to the selection position
            for (int i = 0; i < state.getCardStack().size(); i++) {
                state.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
            }

            //there is one remaining card stack so the Stack count has to be set to 0
            MergeCardsAndMoveToCorner.setStackCount(1);
            if (origin.id() == CharacterSelection.ID) {
                //Transition to Course Selection from CharacterSelection
                putCardsInDeck(CharacterSelection.ID, context);
            } else {
                //Transition to Course Selection from DifficultySelection
                putCardsInDeck(DifficultySelection.ID, context);
                putCardsInDeck(CharacterSelection.ID, context);
            }
        } else {
            //The cards from the Course Selection are moved back to the selection position
            for (int i = 0; i < state.getCardStack().size(); i++) {
                state.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
            }

            //Transition to characterSelection from DifficultySelection

            //there are two remaining card stack so the Stack count has to be set to two
            MergeCardsAndMoveToCorner.setStackCount(2);
            putCardsInDeck(DifficultySelection.ID, context);
        }
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        MenuState state = (MenuState) destination;
        boolean result;
        //check the cards, depending on the gameState, if one card contains a DestTransform
        //if this is the case the cards are still moving and the transition is not over
        if (destination.id() == MainMenu.ID) {
            result = checkForDestTransform(state.getCardStack());
        } else if (destination.id() == CourseSelection.ID) {
            result = checkForDestTransform(state.getCardStack());
        } else { //Transition to CharacterSelection from DifficultySelection
            result = checkForDestTransform(state.getCardStack());
        }
        return result;
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
     *
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
