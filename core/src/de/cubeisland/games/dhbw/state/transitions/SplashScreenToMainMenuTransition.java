package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenToMainMenuTransition extends StateTransition {

    public static final SplashScreenToMainMenuTransition INSTANCE = new SplashScreenToMainMenuTransition();
    private static boolean cardsDrawn=false;
    //TODO choose actual gamemode
    //TODO remember card stack to go back to mainmenu


   // private static List<Entity> cardList = new ArrayList<>();
    @Override
    public void begin(StateContext context) {

    }

    // when the transition is over true is returned else false
    @Override
    public boolean transition(StateContext context, float delta) {
        if(false==cardsDrawn){
            //draw cards and save them in the static vars of Main Menu
            for (int i=0;i<3;i++) {
                Entity card = context.getEngine().getEntitiesFor(Family.getFor(Deck.class)).first().getComponent(Deck.class).drawCard();
                MainMenu.getCardStack().add(card);
                card.add(new DestTransform(new Vector3(-30+30*i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
            }
            cardsDrawn=true;
            return false;
        }else {
            //result indicates if transition is over, when no card contantains a DestTransform all cards are on there destination Position
            boolean result=true;
            for(Entity card:  MainMenu.getCardStack()){
                for(int i=0;i<card.getComponents().size();i++){
                    if(card.getComponents().get(i).getClass()==DestTransform.class){
                        result=false;
                    }
                }
            }
            return result;
        }
    }
}
