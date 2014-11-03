package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

import java.util.ArrayList;
import java.util.List;

public class MergeCardsAndMoveToCorner extends StateTransition {

    private static Entity pickedcard;
    private static List<Entity> cards= new ArrayList<>();
    private static int stacks =0;

    //TODO remember card stack to go back to state and check if entety alredy in stack
    //alternative for check: set pickaple false and check pickable

    public static final MergeCardsAndMoveToCorner INSTANCE = new MergeCardsAndMoveToCorner();
    //TODO extract local var for hardcoded ints
    @Override
    public void begin(StateContext context) {
        pickedcard.add(new DestTransform(new Vector3(75-stacks*40, 75, -249), new Quaternion(new Vector3(0, 0, 0), -100)));
        for (int i=0;i<cards.size();i++){
            cards.get(i).add(new DestTransform(new Vector3(75-stacks*40, 75, -249), new Quaternion(new Vector3(0, 0, 0), -100)));
        }
        pickedcard=null;
        removeAllFromCards();
        stacks++;
    }

    @Override
    public boolean transition(StateContext context, float delta) {
        for (int i=0;i<3;i++) {
            Entity card = context.getEngine().getEntitiesFor(Family.getFor(Deck.class)).first().getComponent(Deck.class).drawCard();
            card.add(new DestTransform(new Vector3(-30+30*i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
            cards.add(card);
        }
        return true;
    }

    public static void setPickedcard(Entity card){
        pickedcard=card;
    }

    public static void addRestCard(Entity card){
        cards.add(card);
    }

    public static void removeRestCard(Entity card){
        cards.remove(card);
    }

    private void removeAllFromCards(){
        int size=cards.size();
        for (int i=0;i<size;i++){
            cards.remove(size-(i+1));
        }
    }
}
