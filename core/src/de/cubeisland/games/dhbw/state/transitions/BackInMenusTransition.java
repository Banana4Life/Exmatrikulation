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
import de.cubeisland.games.dhbw.state.states.CourseSelection;
import de.cubeisland.games.dhbw.state.states.MainMenu;
import de.cubeisland.games.dhbw.state.states.MenuState;

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
        MainMenu state = (MainMenu) destination;
        //The cards from the Main Menu are moved back to the selection position
        for (int i = 0; i < state.getCardStack().size(); i++) {
            state.getCardStack().get(i).add(new DestTransform(new Vector3(-15 + 30 * i, 0, -110), new Quaternion(new Vector3(1, 0, 0), 0)));
        }
        putCardsInDeck(context);

        List<Entity> cards = ((MenuState) origin).getCardStack();
        //cards in list are put back to deck in the right order
        for (int i = 1; i <= cards.size(); i++) {
            context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(cards.get(cards.size() - i));
        }
        while (cards.size() > 0) {
            //card is deleted from cardStack
            cards.remove(0);
        }
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        //    MenuState state = (MenuState) destination;
        //Checks if cards still contain a DestTrasform if this is the case the cards are still moving and the transition is not over
        for (Entity card : context.getEngine().getEntitiesFor(Family.one(DestTransform.class).get())) {
            if (context.getGame().getResources().entities.card.matches(card)) {
                return false;
            }
        }
        return true;
    }


    private void putCardsInDeck(StateContext context) {
        List<Entity> cards = ((MenuState) context.getStateManager().getState(CourseSelection.ID)).getCardStack();
        //cards in list are put back to deck in the right order
        for (int i = 1; i <= cards.size(); i++) {
            context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).putCardOnTop(cards.get(cards.size() - i));
        }
        while (cards.size() > 0) {
            //card is deleted from cardStack
            cards.remove(0);
        }
    }

}
