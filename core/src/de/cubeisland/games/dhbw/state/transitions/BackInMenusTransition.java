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
            if (state.id() == CourseSelection.ID) {
                for (Entity card : state.getCardStack()) {
                    context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(card);
                }
                removeCards(state.getCardStack());
            } else if (state.id() == CharacterSelection.ID) {
                //put cards back on the deck
                for (Entity card : state.getCardStack()) {
                    context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(card);
                }

                // not able to use the local state Variable because an other state is needed
                for (Entity card : ((CourseSelection) context.getStateManager().getState(CourseSelection.ID)).getCardStack()) {
                    context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(card);
                }
                //remove the cards in the states TODO unclear
                removeCards(state.getCardStack());
                // not able to use the local state Variable because an other state is needed
                removeCards(((CourseSelection) context.getStateManager().getState(CourseSelection.ID)).getCardStack());
            } else {
                //TODO from Diff selection
            }
        } else if (destination.id() == CourseSelection.ID) {
            //there is one remainig stack;
            MergeCardsAndMoveToCorner.setStackCount(1);

            if (state.id() == CharacterSelection.ID) {
                for (Entity card : state.getCardStack()) {
                    context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(card);
                }
                removeCards(state.getCardStack());
            } else {
                //TODO from Diff selection
            }
        } else {
            //To characterselection

            //there are two remainig stack;
            MergeCardsAndMoveToCorner.setStackCount(2);

            //TODO all for difficulty selection
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

    private void removeCards(List<Entity> cards) {
        while (cards.size() > 0) {
            cards.remove(0);
        }
    }

}
