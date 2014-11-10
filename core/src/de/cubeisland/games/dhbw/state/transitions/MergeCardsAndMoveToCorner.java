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

/** todo additional authors?
 * this transition is used when moving from main menu to
 * course selection
 *
 * @author Tim Adamek
 */
public class MergeCardsAndMoveToCorner extends StateTransition {

    public static final MergeCardsAndMoveToCorner INSTANCE = new MergeCardsAndMoveToCorner();

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        MenuState state = (MenuState) context.getStateManager().getState(MainMenu.ID);
        Entity pickedCard = state.getPickedcard();
        List<Entity> cardList = state.getCardStack();

        //moves the cards to the top corner
        for (Entity card : cardList) {
            card.add(new DestTransform(new Vector3(-117, 50, -200), new Quaternion(new Vector3(0, 0, 0), -100)));
        }
        //moves the picked card to the top of the stack
        pickedCard.add(new DestTransform(new Vector3(-117, 50, -199), new Quaternion(new Vector3(0, 0, 0), -100)));


        //draw new cards therefor the cardList has to be assigned the cardList of CourseSelection
        cardList = ((MenuState) context.getStateManager().getState(CourseSelection.ID)).getCardStack();

        //draws 3 new cards and add them to the state
        for (int i = 0; i < 3; i++) {
            Entity card = context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).drawCard();
            cardList.add(card);
            card.add(new DestTransform(new Vector3(-30 + 30 * i, 0, -110), new Quaternion(new Vector3(0, 0, 0), -100)));
        }
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        //When no card contains a DestTransform the cards are on there destination Position
        //and the transition is over
        //List<Entity> cardList = ((MenuState) destination).getCardStack();

        for (Entity card : context.getEngine().getEntitiesFor(Family.one(DestTransform.class).get())) {
            if (context.getGame().getResources().entities.card.matches(card)) {
                return false;
            }
        }
        return true;
    }

}
