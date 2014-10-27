package de.cubeisland.games.dhbw.state;

import com.badlogic.ashley.core.Engine;
import de.cubeisland.games.dhbw.DHBWGame;

public class StateContext {
    private final DHBWGame game;
    private final Engine engine;
    private final StateManager stateManager;

    public StateContext(DHBWGame game, Engine engine, StateManager stateManager) {
        this.game = game;
        this.engine = engine;
        this.stateManager = stateManager;
    }

    public DHBWGame getGame() {
        return game;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void transitionTo(short stateTd) {
        this.stateManager.transitionTo(stateTd);
    }

    public Engine getEngine() {
        return engine;
    }
}
