package de.cubeisland.games.dhbw.state;

import com.badlogic.gdx.InputProcessor;

public class StateInputProcessor implements InputProcessor {

    private final StateManager sm;
    private final StateContext context;

    public StateInputProcessor(StateManager stateManager, StateContext context) {
        this.sm = stateManager;
        this.context = context;
    }

    @Override
    public boolean keyDown(int keycode) {
        return sm.getCurrentState().keyDown(context, keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return sm.getCurrentState().keyUp(context, keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return sm.getCurrentState().keyTyped(context, character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return sm.getCurrentState().touchDown(context, screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return sm.getCurrentState().touchUp(context, screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return sm.getCurrentState().touchDragged(context, screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return sm.getCurrentState().mouseMoved(context, screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return sm.getCurrentState().scrolled(context, amount);
    }
}
