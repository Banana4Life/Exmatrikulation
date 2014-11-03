package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

public class SplashScreenToMainMenuTransition extends StateTransition {

    public static final SplashScreenToMainMenuTransition INSTANCE = new SplashScreenToMainMenuTransition();

    //TODO extract local var for ints
    //TODO choose actual gamemode
    //TODO remember card stack to go back to mainmenu
    @Override
    public void begin(StateContext context) {
        for (int i=0;i<3;i++) {
            Entity card = context.getEngine().getEntitiesFor(Family.getFor(Deck.class)).first().getComponent(Deck.class).drawCard();
            MergeCardsAndMoveToCorner.addRestCard(card);
            card.add(new DestTransform(new Vector3(-30+30*i, 0, -150), new Quaternion(new Vector3(0, 0, 0), -100)));
        }
    }

    @Override
    public boolean transition(StateContext context, float delta) {

        return true;
    }
}
