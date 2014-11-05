package de.cubeisland.games.dhbw.state;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.input.InputMultiplexer;
import de.cubeisland.games.dhbw.util.NotNull;
import de.cubeisland.games.dhbw.util.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The state manager manages all states and transitions and controls their life cycle.
 *
 * @author Phillip Schichtel
 */
public class StateManager {

    public static final MetaState TRANSITION = new TransitionState();
    public static final MetaState START = new StartState();
    public static final MetaState END = new EndState();

    private final StateContext context;
    private GameState currentState;
    private TransitionWrapper currentTransition;
    private final Map<Short, GameState> states;
    private final Map<Integer, TransitionWrapper> transitions;

    /**
     * Constructs a new state manager with the the required dependencies.
     *
     * @param game The main game instance
     * @param engine the entity engine
     * @param camera the camera
     * @param input the root input multiplexer
     */
    public StateManager(DHBWGame game, Engine engine, Camera camera, InputMultiplexer input) {
        this.context = new StateContext(game, engine, camera, this);
        input.append(new StateInputProcessor(this, this.context));
        this.states = new HashMap<>();
        this.transitions = new HashMap<>();
        this.currentState = START;
    }

    /**
     * Adds a new state into the system.
     * The state must return a unique short ID different from 0.
     *
     * @param state the state to add
     * @return fluent interface
     *
     * @throws java.lang.IllegalArgumentException if the state has the meta state ID 0
     * @throws java.lang.IllegalStateException if there is already a state registered with the same ID
     */
    @NotNull
    public StateManager addState(GameState state) {
        final short id = state.id();
        if (id == MetaState.ID) {
            throw new IllegalArgumentException("State id " + MetaState.ID + " is reserved!");
        }
        GameState existing = this.states.get(id);
        if (existing != null) {
            throw new IllegalStateException("There is already a state registered on id " + id + ": " + existing.getClass().getName());
        }
        this.states.put(id, state);
        return this;
    }

    /**
     * Returns the state with the given state.
     *
     * @param id the state ID
     * @return the state
     *
     * @throws java.lang.IllegalArgumentException if there was not state found with the given ID
     */
    @NotNull
    public GameState getState(short id) {
        if (id == MetaState.ID) {
            return START;
        }
        GameState state = this.states.get(id);
        if (state == null) {
            throw new IllegalArgumentException("No state found for id: " + id);
        }
        return state;
    }

    @SuppressWarnings("unchecked")
    public <T extends GameState> T getStateTyped(short id) {
        return (T)getState(id);
    }

    /**
     * Removes the state with the given state ID.
     *
     * @param id the state ID
     * @return fluent interface
     *
     * @throws java.lang.IllegalArgumentException if there is no state registered with that ID
     * @throws java.lang.IllegalArgumentException if the state to remove is a meta state
     */
    @NotNull
    public StateManager removeState(short id) {
        return removeState(getState(id));
    }

    /**
     * Removes the given state.
     *
     * @param state the state to remove
     * @return fluent interface
     *
     * @throws java.lang.IllegalArgumentException if the state to remove is a meta state
     */
    @NotNull
    public StateManager removeState(GameState state) {
        if (state instanceof MetaState) {
            throw new IllegalArgumentException("Meta states cannot be removed!");
        }
        Iterator<Map.Entry<Integer, TransitionWrapper>> it = this.transitions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, TransitionWrapper> entry = it.next();
            if (originComponent(entry.getKey()) == state.id() || destinationComponent(entry.getKey()) == state.id()) {
                it.remove();
            }
        }
        return this;
    }

    /**
     * Returns the currently active state.
     * During transitions this will return the meta state {@link de.cubeisland.games.dhbw.state.StateManager.TransitionState}
     *
     * @return the current state
     */
    @NotNull
    public GameState getCurrentState() {
        return this.currentState;
    }

    /**
     * Returns the currently active transition.
     *
     * @return the active transition or null of not transitioning right now
     */
    @Nullable
    public StateTransition getCurrentTransition() {
        if (this.currentTransition != null) {
            return this.currentTransition.getTransition();
        }
        return null;
    }

    /**
     * Returns the start state.
     * The start state is the state that is the target of the initial transition.
     * Only one state can be targeted by this transition, so this state is unique.
     *
     * @return the start state or null of no initial transition has been defined yet
     */
    @Nullable
    public GameState getStartState() {
        for (Map.Entry<Integer, TransitionWrapper> entry : this.transitions.entrySet()) {
            if (originComponent(entry.getKey()) == MetaState.ID) {
                return entry.getValue().getDestination();
            }
        }
        return null;
    }

    /**
     * Adds a transition from one state to another.
     *
     * @param fromId the state ID origin state
     * @param toId the state ID of the destination state
     * @param transition the transition function
     * @return fluent interface
     *
     * @throws java.lang.IllegalArgumentException if the transition would define a second start state
     * @throws java.lang.IllegalArgumentException if the one of the states or both are unknown
     */
    @NotNull
    public StateManager addTransition(short fromId, short toId, StateTransition transition) {
        final GameState from;
        if (fromId == MetaState.ID) {
            GameState start = getStartState();
            if (start != null) {
                throw new IllegalArgumentException("There is already a start state defined: " + start.getClass().getName() + " (" + start.id() + ")");
            }
            from = START;
        } else {
            from = getState(fromId);
        }

        final GameState to = (toId == MetaState.ID ? END : getState(toId));

        this.transitions.put(combine(fromId, toId), new TransitionWrapper(transition, from, to));
        return this;
    }

    /**
     * Removes the transition between the given states
     *
     * @param from the origin state ID
     * @param to the destination state ID
     * @return fluent interface
     */
    @NotNull
    public StateManager removeTransition(short from, short to) {
        this.transitions.remove(combine(from, to));
        return this;
    }

    /**
     * Starts the state system by starting the initial transition to the start state.
     *
     * @deprecated fluent interface
     *
     * @throws java.lang.IllegalStateException of not start state is defined
     */
    @NotNull
    public StateManager start() {
        GameState start = getStartState();
        if (start == null) {
            throw new IllegalStateException("No start state (state with a transition from a meta state) found!");
        }
        transitionTo(start.id());
        return this;
    }

    /**
     * Starts the transition to another state.
     *
     * @param stateId the state ID to transition to
     * @return fluent interface
     *
     * @throws java.lang.IllegalStateException if the transition was started during a transition
     * @throws java.lang.IllegalArgumentException if the destination state equals the current state (a loop)
     * @throws java.lang.IllegalArgumentException if there is not transition defined from the current to the destination state
     */
    @NotNull
    public StateManager transitionTo(short stateId) {
        if (getCurrentTransition() != null) {
            throw new IllegalStateException("Starting a new transition from the transition function is not allowed!");
        }
        if (this.currentState.id() == stateId) {
            throw new IllegalArgumentException("Loops are not allowed! State " + this.currentState.id() + " tried to transition to itself.");
        }
        TransitionWrapper transition = this.transitions.get(combine(this.currentState.id(), stateId));
        if (transition == null) {
            throw new IllegalArgumentException("There is no transition defined from id " + this.currentState.id() + " to " + stateId);
        }
        beginTransition(transition);
        return this;
    }

    private void beginTransition(TransitionWrapper transition) {
        this.currentTransition = transition;
        transition.begin(this.context, transition.getOrigin(), transition.getDestination());
        switchState(TRANSITION);
    }

    private void finishTransition() {
        TransitionWrapper transition = this.currentTransition;
        this.currentTransition = null;
        transition.finish(this.context, transition.getOrigin(), transition.getDestination());
        switchState(transition.getDestination());
    }

    private void switchState(GameState newState) {
        final GameState current = getCurrentState();
        if (current != null) {
            current.onLeave(context, newState);
        }
        this.currentState = newState;
        if (newState instanceof TransitionState) {
            System.out.println("Transitioning...");
        } else {
            System.out.println("Now in " + newState);
        }
        newState.onEnter(context, current);
    }

    /**
     * This method should be called from the game's update() method to update the state system
     *
     * @param delta the delta time since the last tick
     */
    public void update(float delta) {
        if (this.currentTransition != null) {
            if (this.currentTransition.transition(this.context, this.currentTransition.getOrigin(), this.currentTransition.getDestination(), delta)) {
                finishTransition();
            }
            return;
        }
        if (!(this.currentState instanceof MetaState)) {
            this.currentState.update(context, delta);
        }
    }

    /**
     * Combines two shorts bitwise into a single integer.
     * This method is used to combine state IDs to the transition index.
     *
     * @param a the left short value
     * @param b the right short value
     * @return the combined integer
     */
    private static int combine(short a, short b) {
        return (((int) a) << 16) | b;
    }

    /**
     * This method extracts the origin state ID from a transition index
     *
     * @param combined the transition index
     * @return the short ID of the origin state
     */
    private static short originComponent(int combined) {
        return (short)(combined >> 16);
    }

    /**
     * This method extracts the destination state ID from a transition index
     *
     * @param combined the transition index
     * @return the short ID of the destination state
     */
    private static short destinationComponent(int combined) {
        return (short)(combined & 0xFFFF);
    }

    /**
     * This class wraps a transition and holds the origin and the destination states
     */
    private static final class TransitionWrapper extends StateTransition {

        private final StateTransition transition;
        private final GameState origin;
        private final GameState destination;

        private TransitionWrapper(StateTransition transition, GameState origin, GameState destination) {
            this.transition = transition;
            this.origin = origin;
            this.destination = destination;
        }

        public StateTransition getTransition() {
            return transition;
        }

        public GameState getOrigin() {
            return origin;
        }

        public GameState getDestination() {
            return destination;
        }

        @Override
        public void begin(StateContext context, GameState origin, GameState destination) {
            this.transition.begin(context, origin, destination);
        }

        @Override
        public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
            return this.transition.transition(context, origin, destination, delta);
        }

        @Override
        public void finish(StateContext context, GameState origin, GameState destination) {
            this.transition.finish(context, origin, destination);
        }
    }

    /**
     * Abstract meta state which enforces the ID 0 for all meta states
     */
    private static abstract class MetaState extends GameState {

        public static final short ID = 0;

        @Override
        public final short id() {
            return ID;
        }
    }

    public static final class TransitionState extends MetaState {
        private TransitionState() {
        }

        @Override
        public boolean keyTyped(StateContext context, char character) {
            return false;
        }
    }

    public static final class StartState extends MetaState {
        private StartState() {
        }
    }

    /**
     * The end state is the state that is entered when transitioning to the meta state.
     * This state simply exits the game.
     */
    public static final class EndState extends MetaState {

        private EndState() {
        }

        @Override
        public void onEnter(StateContext context, GameState from) {
            context.getGame().exit();
        }
    }
}
