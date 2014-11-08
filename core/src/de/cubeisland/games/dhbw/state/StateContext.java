package de.cubeisland.games.dhbw.state;

import com.badlogic.ashley.core.Engine;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Camera;
import de.cubeisland.games.dhbw.util.NotNull;

/**
 * This class holds objects and helper methods for state callbacks.
 * The context can be created once per state manager as it will not chance.
 *
 * @author Phillip Schichtel
 */
public class StateContext {
    private final DHBWGame game;
    private final Engine engine;
    private final Camera camera;
    private final StateManager stateManager;

    /**
     * Constructs a new instance.
     *
     * @param game         the main game instance
     * @param engine       the entity engine
     * @param camera       the camera
     * @param stateManager the state manager
     */
    public StateContext(DHBWGame game, Engine engine, Camera camera, StateManager stateManager) {
        this.game = game;
        this.engine = engine;
        this.camera = camera;
        this.stateManager = stateManager;
    }

    /**
     * Returns the main game instance.
     *
     * @return the main game instance
     */
    @NotNull
    public DHBWGame getGame() {
        return game;
    }

    /**
     * Returns the state manager
     *
     * @return the state manager
     */
    @NotNull
    public StateManager getStateManager() {
        return stateManager;
    }

    /**
     * Starts a transition to the given state.
     *
     * @param stateTd the destination state iD
     * @throws java.lang.IllegalStateException    if the transition was started during a transition
     * @throws java.lang.IllegalArgumentException if the destination state equals the current state (a loop)
     * @throws java.lang.IllegalArgumentException if there is not transition defined from the current to the destination state
     */
    public void transitionTo(short stateTd) {
        this.stateManager.transitionTo(stateTd);
    }

    /**
     * Returns the entity engine.
     *
     * @return the entity engine
     */
    @NotNull
    public Engine getEngine() {
        return engine;
    }

    /**
     * Returns the the camera.
     *
     * @return the camera
     */
    @NotNull
    public Camera getCamera() {
        return camera;
    }
}
