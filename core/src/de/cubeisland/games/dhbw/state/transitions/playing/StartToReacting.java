package de.cubeisland.games.dhbw.state.transitions.playing;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.DestTransform;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.MainMenu;
import de.cubeisland.games.dhbw.state.states.playing.ReactingState;

/**
 * Created by Tim Adamek on 08.11.2014.
 */
public class StartToReacting extends StateTransition {

    public static final StartToReacting  INSTANCE = new StartToReacting ();

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        System.out.println("Hi");
        for (int i = 0; i < ReactingState.STARTCARDCOUNT; i++) {
            Entity card =context.getEngine().getEntitiesFor(Family.one(Deck.class).get()).first().getComponent(Deck.class).drawCard();
            ((ReactingState)context.getStateManager().getState(ReactingState.ID)).getCardsInHand().add(card);
            card.add(new DestTransform(new Vector3(-30 + 30 * i, -50, -150), new Quaternion(new Vector3(1, 0, 0), 0)));
        }
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        return true;
    }
}
