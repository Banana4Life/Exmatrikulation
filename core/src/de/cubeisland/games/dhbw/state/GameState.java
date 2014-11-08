package de.cubeisland.games.dhbw.state;

/**
 * This class defines an abstract game state with all of the callbacks for both state changes and input.
 */
public abstract class GameState {

    /**
     * Returns the unique ID of this state.
     * This is the only mandatory method that need to be implemented.
     *
     * @return the short state ID
     */
    public abstract short id();

    /**
     * This callback will be called as soon as this state is the new active state.
     * During this callback {@link StateManager#getCurrentState()} will already return this state.
     *
     * @param context the context
     * @param from    the previous state
     */
    public void onEnter(StateContext context, GameState from) {

    }

    /**
     * This callback will be called right before this state will be left.
     * During this callback {@link StateManager#getCurrentState()} will still return this state.
     *
     * @param context the context
     * @param to      the next state
     */
    public void onLeave(StateContext context, GameState to) {

    }

    /**
     * This callback will be called every tick while the state is the active state
     *
     * @param context the context
     * @param delta   the delta time since the last tick
     */
    public void update(StateContext context, float delta) {

    }

    /**
     * This callback will be called when a keyboard key is pressed down while this state is active.
     *
     * @param context the context
     * @param keycode the code of the key pressed (compare with {@link com.badlogic.gdx.Input.Keys})
     * @return true if the event was handled, false otherwise
     */
    public boolean keyDown(StateContext context, int keycode) {
        return false;
    }

    /**
     * This callback will be called when a keyboard key is released while this state is active.
     *
     * @param context the context
     * @param keycode the code of the released key (compare with {@link com.badlogic.gdx.Input.Keys})
     * @return true if the event was handled, false otherwise
     */
    public boolean keyUp(StateContext context, int keycode) {
        return false;
    }

    /**
     * This callback will be called when a character has been typed on the keyboard while this state is active.
     *
     * @param context   the context
     * @param character the character that has been typed
     * @return true if the event was handled, false otherwise
     */
    public boolean keyTyped(StateContext context, char character) {
        if (Character.isDigit(character)) {
            context.transitionTo((short) (character - '0'));
        }
        return false;
    }

    /**
     * This callback will be called when a mouse button is pressed or a finger touches the screen down while this
     * state is active.
     *
     * @param context the context
     * @param screenX the mouse position on the X axis
     * @param screenY the mouse position on the Y axis
     * @param pointer the pointer/finger ID
     * @param button  the button that has been pressed down (compare with {@link com.badlogic.gdx.Input.Buttons})
     * @return true if the event was handled, false otherwise
     */
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * This callback will be called when a mouse button is released or a finger stops touching the screen while
     * this state is active.
     *
     * @param context the context
     * @param screenX the mouse position on the X axis
     * @param screenY the mouse position on the Y axis
     * @param pointer the pointer/finger ID
     * @param button  the button that has been released (compare with {@link com.badlogic.gdx.Input.Buttons})
     * @return true if the event was handled, false otherwise
     */
    public boolean touchUp(StateContext context, int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * This callback will be called when a finger dragged over the screen while this state is active.
     *
     * @param context the context
     * @param screenX the mouse position on the X axis
     * @param screenY the mouse position on the Y axis
     * @param pointer the finger ID
     * @return true if the event was handled, false otherwise
     */
    public boolean touchDragged(StateContext context, int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * This callback will be called when the mouse is moved over the screen while this state is active.
     *
     * @param context the context
     * @param screenX the mouse position on the X axis
     * @param screenY the mouse position on the Y axis
     * @return true if the event was handled, false otherwise
     */
    public boolean mouseMoved(StateContext context, int screenX, int screenY) {
        return false;
    }

    /**
     * This callback will be called when the scroll wheel is used while this state is active.
     *
     * @param context the context
     * @param amount  the amount scrolled (the sign indicated the direction)
     * @return true if the event was handled, false otherwise
     */
    public boolean scrolled(StateContext context, int amount) {
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + id() + ")";
    }
}
