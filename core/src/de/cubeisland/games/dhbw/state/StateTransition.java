package de.cubeisland.games.dhbw.state;

/** todo some params in javadoc for methods not documented
 * Classes derived from this define transitions between two states.
 *
 * @author Phillip Schichtel
 */
public abstract class StateTransition {

    /**
     * This callback is being called when the transition is activated by the state manager.
     * At this point {@link StateManager#getCurrentState()} will return the meta state {@link de.cubeisland.games.dhbw.state.StateManager.TransitionState}.
     *
     * @param context     the context
     * @param origin
     * @param destination
     */
    public void begin(StateContext context, GameState origin, GameState destination) {

    }

    /**
     * This callback is the transition function. It acts as the update() function during the transition.
     * The transition function is called every tick until it returns true to signal its completion.
     *
     * @param context     the context
     * @param origin
     * @param destination @return false to keep transitioning, true to signal completion
     * @param delta       the delta time since the last tick
     */
    public abstract boolean transition(StateContext context, GameState origin, GameState destination, float delta);

    /**
     * This callback is being called on deactivation of the transition in the same tick when {@link #transition(StateContext, GameState, GameState, float)}
     * returned true (signaled completion).
     *
     * @param context     the context
     * @param origin
     * @param destination
     */
    public void finish(StateContext context, GameState origin, GameState destination) {

    }

}
