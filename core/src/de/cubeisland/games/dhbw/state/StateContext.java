package de.cubeisland.games.dhbw.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import de.cubeisland.games.dhbw.DHBWGame;

public class StateContext {
    private final DHBWGame game;
    private final Engine engine;
    private final Camera camera;
    private final StateManager stateManager;

    public StateContext(DHBWGame game, Engine engine, Camera camera, StateManager stateManager) {
        this.game = game;
        this.engine = engine;
        this.camera = camera;
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

    public Camera getCamera() {
        return camera;
    }
}
