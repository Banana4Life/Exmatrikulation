package de.cubeisland.games.dhbw.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.cubeisland.games.dhbw.state.StateManager;

/** todo author
 * This is the global input processor which acts as the fallback of the input multiplexer.
 * It can be used to handle global key bindings
 */
public class GlobalInputProcessor implements InputProcessor {
    private final PerspectiveCamera camera;
    private final Engine engine;
    private final StateManager sm;

    /**
     * Constructs a new instance
     *
     * @param camera       the camera used by the game
     * @param engine       the ashley entity engine used to process entities in the game
     * @param stateManager the state manager
     */
    public GlobalInputProcessor(PerspectiveCamera camera, Engine engine, StateManager stateManager) {
        this.camera = camera;
        this.engine = engine;
        this.sm = stateManager;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
