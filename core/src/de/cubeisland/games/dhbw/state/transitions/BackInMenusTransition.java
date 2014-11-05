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

import java.util.ArrayList;
import java.util.List;

public class BackInMenusTransition extends StateTransition {

    public static final BackInMenusTransition INSTANCE = new BackInMenusTransition();

    private static boolean cardsMoving = false;

    //TODO Add Card back to the top of the Deck
    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {

        if (destination.id() == MainMenu.ID) {
            //there is no remainig stack;
            MergeCardsAndMoveToCorner.setStacks(0);

            if (origin.id() == CourseSelection.ID) {
                for (Entity card : CourseSelection.getCardStack()) {
                    context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(card);
                }
                removeCards(CourseSelection.getCardStack());
            } else if (origin.id() == CharacterSelection.ID) {
                //put cards back on the deck
                for (Entity card : CharacterSelection.getCardStack()) {
                    context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(card);
                }
                for (Entity card : CourseSelection.getCardStack()) {
                    context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(card);
                }
                //remove the cards in the state
                removeCards(CourseSelection.getCardStack());
                removeCards(CharacterSelection.getCardStack());//TODO rads not removed--> add them to deck instead, bug for all card where there are cards which have to be moved to the deck
            } else {
                //TODO from Diff selection
            }
        } else if (destination.id() == CourseSelection.ID) {
            //there is one remainig stack;
            MergeCardsAndMoveToCorner.setStacks(1);

            if (origin.id() == CharacterSelection.ID) {
                for (Entity card : CharacterSelection.getCardStack()) {
                    context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(card);
                }
                removeCards(CharacterSelection.getCardStack());
            } else {
                //TODO from Diff selection
            }
        } else {
            //To characterselection

            //there are two remainig stack;
            MergeCardsAndMoveToCorner.setStacks(2);

            //TODO all for difficulty selection
        }
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        if(!cardsMoving) {
            if (destination.id() == MainMenu.ID) {
                for (int i = 0; i < MainMenu.getCardStack().size(); i++) {
                    MainMenu.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
                }
            } else if (destination.id() == CourseSelection.ID) {
                for (int i = 0; i < CourseSelection.getCardStack().size(); i++) {
                    CourseSelection.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
                }
            } else {
                for (int i = 0; i < CharacterSelection.getCardStack().size(); i++) {
                    CharacterSelection.getCardStack().get(i).add(new DestTransform(new Vector3(-30 + 30 * i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
                }
                //To Characterselection
            }
            cardsMoving = true;
            return false;
        } else {
            boolean result;
            //check in the cardStack -depending on the gamestate- if one card contains a DestTransform
            //if this is the case the cards are still moving and the transition is not over
            if (destination.id() == MainMenu.ID) {
                result = chekForDestTransform(MainMenu.getCardStack());
            } else if (destination.id() == CourseSelection.ID) {
                result = chekForDestTransform(CourseSelection.getCardStack());
            } else { //To Characterselection
                result = chekForDestTransform(CharacterSelection.getCardStack());
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
