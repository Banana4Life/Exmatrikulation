package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateManager;
import de.cubeisland.games.dhbw.state.states.playing.*;
import de.cubeisland.games.dhbw.state.transitions.NOPTransition;
import de.cubeisland.games.dhbw.state.transitions.playing.*;

import java.util.ArrayList;
import java.util.List;

public class Playing extends GameState {

    public static final short ID = 4;
    private StateManager stateManager;

    @Override
    public short id() {
        return ID;
    }

    @Override
    public void onEnter(StateContext context, GameState from) {

        this.stateManager = new StateManager(context.getGame(), context.getEngine(), context.getCamera(), context.getGame().getInputMultiplexer())
                .addState(new ReactingState())
                .addState(new DiscardingCardsState())
                .addState(new GameLostState())
                .addState(new GameWonState())
                .addState(new DecidingState())
                .addTransition(StateManager.StartState.ID,  ReactingState.ID,           StartToReacting.INSTANCE)
                .addTransition(ReactingState.ID,            DecidingState.ID,           ThrowDiceTransition.INSTANCE)
                .addTransition(DecidingState.ID,            ReactingState.ID,           NextEventTransition.INSTANCE)
                .addTransition(DecidingState.ID,            GameLostState.ID,           GameLostTransition.INSTANCE)
                .addTransition(DecidingState.ID,            GameWonState.ID,            GameWonTransition.INSTANCE)
                .addTransition(DecidingState.ID,            DiscardingCardsState.ID,    ToMuchCadsTransition.INSTANCE)
                .addTransition(DiscardingCardsState.ID,     ReactingState.ID,           NextEventTransition.INSTANCE)
                .addTransition(GameWonState.ID,             StateManager.EndState.ID,   NOPTransition.INSTANCE)
                .addTransition(GameLostState.ID,            StateManager.EndState.ID,   NOPTransition.INSTANCE)
                .start();
    }

    @Override
    public boolean keyDown(StateContext context, int keycode) {
//        if (keycode == Input.Keys.W) {
//            context.getEngine().getEntitiesFor(Family.all(Deck.class).get()).first().getComponent(Deck.class).drawCard();
//            return true;
//        }
        return false;
    }

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
//        if (button != Input.Buttons.LEFT) {
//            return false;
//        }
//        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
//        if (e != null) {
//            e.remove(Pickable.class);
//            e.add(new Picked());
//            e.getComponent(Transform.class).move(0, 0, 1);
//            return true;
//        }
        return false;
    }

    @Override
    public boolean touchUp(StateContext context, int screenX, int screenY, int pointer, int button) {
//        ImmutableArray<Entity> entities = context.getEngine().getEntitiesFor(Family.all(Picked.class).get());
//        if (entities.size() == 0) {
//            return false;
//        }
//        for (int i = 0; i < entities.size(); ++i) {
//            Entity entity = entities.get(i);
//            entity.remove(Picked.class);
//            entity.add(new Pickable());
//        }
        return true;
    }

}
