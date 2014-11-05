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
import de.cubeisland.games.dhbw.state.states.CharacterSelection;
import de.cubeisland.games.dhbw.state.states.CourseSelection;
import de.cubeisland.games.dhbw.state.states.MainMenu;
import de.cubeisland.games.dhbw.state.states.MenuState;

import java.util.List;

public class BackInMenusTransition extends StateTransition {

    public static final BackInMenusTransition INSTANCE = new BackInMenusTransition();

    private static boolean cardsMoving = false;

    //TODO test if right cards are moved

    //TODO JavaDoc
    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        //state saves the origin state and casts it to menu state,
        //this allows using the underlying menu methods from the original states
        MenuState state = (MenuState) origin;
        if (destination.id() == MainMenu.ID) {
            //there is no remainig stack;
            MergeCardsAndMoveToCorner.setStackCount(0);
            //TODO create new extra class which implemments getCardStack/getpickedCard
            if (origin.id() == CourseSelection.ID) {
                putCardsInDeck(state.getCardStack(), context);
                removeCards(state.getCardStack());
            } else if (origin.id() == CharacterSelection.ID) {

                //put cards back on the deck
                putCardsInDeck(state.getCardStack(), context);

                // not able to use the local state Variable because an other state is needed TODO bad comment
                putCardsInDeck(((CourseSelection) context.getStateManager().getState(CourseSelection.ID)).getCardStack(), context);

                //remove the cards in the states TODO unclear
                removeCards(state.getCardStack());
                // not able to use the local state Variable, so the state has to be accessed via the stateManager
                removeCards(((CourseSelection) context.getStateManager().getState(CourseSelection.ID)).getCardStack());

            } else {

                //put cards back on the deck
                putCardsInDeck(state.getCardStack(), context);

                // not able to use the local state Variable because an other state is needed TODO bad comment
                putCardsInDeck(((CharacterSelection) context.getStateManager().getState(CharacterSelection.ID)).getCardStack(), context);
                putCardsInDeck(((CourseSelection) context.getStateManager().getState(CourseSelection.ID)).getCardStack(), context);

                //remove the cards in the states TODO unclear
                removeCards(state.getCardStack());
                // not able to use the local state Variable, so the state has to be accessed via the stateManager
                removeCards(((CharacterSelection) context.getStateManager().getState(CharacterSelection.ID)).getCardStack());
                removeCards(((CourseSelection) context.getStateManager().getState(CourseSelection.ID)).getCardStack());

            }
        } else if (destination.id() == CourseSelection.ID) {

            //there is one remainig stack;
            MergeCardsAndMoveToCorner.setStackCount(1);

            if (state.id() == CharacterSelection.ID) {

                putCardsInDeck(state.getCardStack(),context);
                removeCards(state.getCardStack());

            } else {
                putCardsInDeck(state.getCardStack(),context);
                removeCards(state.getCardStack());

                putCardsInDeck(((CharacterSelection)context.getStateManager().getState(CharacterSelection.ID)).getCardStack(),context);
                removeCards(((CharacterSelection)context.getStateManager().getState(CharacterSelection.ID)).getCardStack());
            }

        } else {

            //there are two remainig stack;
            MergeCardsAndMoveToCorner.setStackCount(2);
            //To characterselection
            //only possible from DifficultySelection
            putCardsInDeck(state.getCardStack(),context);
            removeCards(state.getCardStack());
        }
    }

    //TODO JavaDoc
    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        //state saves the destination state and casts it to menu state,
        //this allows using the underlying menu methods from the original states
        MenuState state = (MenuState) destination;
        if (!cardsMoving) {
            if (state.id() == MainMenu.ID) {
                for (int i = 0; i < state.getCardStack().size(); i++) {
                    state.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
                }
            } else if (state.id() == CourseSelection.ID) {
                for (int i = 0; i < state.getCardStack().size(); i++) {
                    state.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
                }
            } else {
                //To Characterselection
                for (int i = 0; i < state.getCardStack().size(); i++) {
                    state.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
                }
            }
            cardsMoving = true;
            return false;
        } else {
            boolean result;
            //check in the cardStack -depending on the gamestate- if one card contains a DestTransform
            //if this is the case the cards are still moving and the transition is not over
            if (state.id() == MainMenu.ID) {
                result = chekForDestTransform(state.getCardStack());
            } else if (state.id() == CourseSelection.ID) {
                result = chekForDestTransform(state.getCardStack());
            } else {
                //To Characterselection
                result = chekForDestTransform(state.getCardStack());
            }
            if (result) {
                cardsMoving = false;
            }
            return result;
        }
    }


    private void putCardsInDeck(List<Entity> cards, StateContext context) {
        for (Entity card : cards) {
            context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(card);
        }
    }


    private boolean chekForDestTransform(List<Entity> cards) {
        boolean result = true;
        for (Entity card : cards) {
            for (int i = 0; i < card.getComponents().size(); i++) {
                if (card.getComponents().get(i).getClass() == DestTransform.class) {
                    result = false;
                }
            }
        }
        return result;
    }


    //CHANE to need stateID
    private void removeCards(List<Entity> cards) {
        while (cards.size() > 0) {
            cards.remove(0);
        }
    }

}
