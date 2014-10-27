package de.cubeisland.games.dhbw.state;

import com.badlogic.ashley.core.Entity;

public abstract class GameState {
    public abstract short id();

    public void onEnter(StateContext context, GameState from) {

    }

    public void onLeave(StateContext context, GameState to) {

    }

    public void update(StateContext context, float delta) {

    }

    public void entityTouched(Entity e, float x, float y) {

    }

    public boolean keyDown(StateContext context, int keycode) {
        return false;
    }

    public boolean keyUp(StateContext context, int keycode) {
        return false;
    }

    public boolean keyTyped(StateContext context, char character) {
        if (Character.isDigit(character)) {
            context.transitionTo((short)(character - '0'));
        }
        return false;
    }

    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(StateContext context, int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(StateContext context, int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(StateContext context, int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(StateContext context, int amount) {
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + id() + ")";
    }
}
