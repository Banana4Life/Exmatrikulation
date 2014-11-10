package de.cubeisland.games.dhbw.state.transitions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.*;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;
import de.cubeisland.games.dhbw.state.states.DecidingState;

/**
 * this is the transition to the next semester
 *
 * @author Tim Adamek
 */
public class NextSemsterTransition extends StateTransition {

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        int lastSemester = ((DecidingState)context.getStateManager().getState(DecidingState.ID)).getCurrentSemester();
        ((DecidingState)context.getStateManager().getState(DecidingState.ID)).setCurrentSemester(lastSemester+1);

        DHBWGame game = context.getGame();

        Entity[] entities = game.getEngine().getEntitiesFor(Family.one(Card.class, Deck.class, CardHand.class,Dice.class,ToMenu.class, PlayerChar.class).get()).toArray(Entity.class);
        for (Entity entity : entities) {
            game.getEngine().removeEntity(entity);
        }

        Entity semester;
        switch (lastSemester){
            case 1:
                semester = game.getEntityFactory().createImage("images/semester2.png", new Vector3(0, 0, -280), .344f);
                break;
            case 2:
                semester = game.getEntityFactory().createImage("images/semester3.png", new Vector3(0, 0, -280), .344f);
                break;
            case 3:
                semester = game.getEntityFactory().createImage("images/semester4.png", new Vector3(0, 0, -280), .344f);
                break;
            case 4:
                semester = game.getEntityFactory().createImage("images/semester5.png", new Vector3(0, 0, -280), .344f);
                break;
            case 5:
                semester = game.getEntityFactory().createImage("images/semester6.png", new Vector3(0, 0, -280), .344f);
                break;
            default:
                semester = game.getEntityFactory().createImage("images/semester1.png", new Vector3(0, 0, -280), .344f);
                break;
        }
        context.getEngine().addEntity(semester);
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        return true;
    }

}
