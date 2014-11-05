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
import de.cubeisland.games.dhbw.state.states.DifficultySelection;
import de.cubeisland.games.dhbw.state.states.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class MergeCardsAndMoveToCorner extends StateTransition {

    private static int stacks =0;

    public static int fromState;
    public static int toState;

    private static boolean cardsDrawn=false;

    public static final MergeCardsAndMoveToCorner INSTANCE = new MergeCardsAndMoveToCorner();

    /**
     * This method moves the cards to the corner and updates the cards in the static variable of the state
     * @param context
     * @param origin
     * @param destination
     */
    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        //saving static Card/CardList of current States in a local variable
        //to only need to check for setting the variables and not for the complete function
        Entity pickedcard;
        List<Entity> cardList= new ArrayList<>();

        if(fromState== MainMenu.ID){
            pickedcard=MainMenu.getPickedcard();
            cardList=MainMenu.getCardStack();
        }else if(fromState== CourseSelection.ID){
            pickedcard=CourseSelection.getPickedcard();
            cardList=CourseSelection.getCardStack();
        }else /*if(context.getStateManager().getCurrentState().id()==CharacterSelection.ID)*/{ //TODO ensure that stateCounter always right value
            pickedcard=CharacterSelection.getPickedcard();
            cardList=CharacterSelection.getCardStack();
        }


        //moves the cards to the top corner
        for (Entity card:cardList){
            card.add(new DestTransform(new Vector3(75-stacks*40, 75, -250), new Quaternion(new Vector3(0, 0, 0), -100)));
        }
        //moves the picked card to the top of the stack
        pickedcard.add(new DestTransform(new Vector3(75-stacks*40, 75, -249), new Quaternion(new Vector3(0, 0, 0), -100)));
        stacks++;
    }

    /**
     * This method draws the next 3 cards and saves them in the
     * state after the transition
     *  @param context
     * @param origin
     * @param destination @return
     * @param delta
     */
    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        //checking for state and saving to next state
        int test = context.getStateManager().getCurrentState().id();
        System.out.print(test);
        List<Entity> cardList;
        if(fromState== MainMenu.ID){
            cardList=CourseSelection.getCardStack();
        }else if(fromState == CourseSelection.ID){
            cardList=CharacterSelection.getCardStack();
        }else /*if(context.getStateManager().getCurrentState().id()==CharacterSelection.ID)*/{
            cardList=DifficultySelection.getCardStack();
        }

        //draws 3 new cards
        if (!cardsDrawn) {
            for (int i = 0; i < 3; i++) {
                Entity card = context.getEngine().getEntitiesFor(Family.getFor(Deck.class)).first().getComponent(Deck.class).drawCard();
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

    public static int getStacks() {
        return stacks;
    }

    public static void setStacks(int stacks) {
        MergeCardsAndMoveToCorner.stacks = stacks;
    }

}
