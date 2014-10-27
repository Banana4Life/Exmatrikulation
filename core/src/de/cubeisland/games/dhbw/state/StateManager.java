package de.cubeisland.games.dhbw.state;

import com.badlogic.ashley.core.Engine;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.input.InputMultiplexer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StateManager {

    public static final MetaState TRANSITION = new TransitionState();
    public static final MetaState START = new StartState();
    public static final MetaState END = new EndState();

    private final DHBWGame game;
    private final StateInputProcessor input;
    private final StateContext context;
    private GameState currentState;
    private TransitionWrapper currentTransition;
    private final Map<Short, GameState> states;
    private final Map<Integer, TransitionWrapper> transitions;

    public StateManager(DHBWGame game, Engine engine, InputMultiplexer input) {
        this.game = game;
        this.context = new StateContext(game, engine, this);
        this.input = new StateInputProcessor(this, this.context);
        input.append(this.input);
        this.states = new HashMap<>();
        this.transitions = new HashMap<>();
        this.currentState = START;
    }

    public DHBWGame getGame() {
        return this.game;
    }

    public StateManager addState(GameState state) {
        final short id = state.id();
        if (id == MetaState.ID) {
            throw new IllegalArgumentException("State id " + MetaState.ID + " is reserved!");
        }
        GameState existing = this.states.get(id);
        if (existing != null) {
            throw new IllegalArgumentException("There is already a state registered on id " + id + ": " + existing.getClass().getName());
        }
        this.states.put(id, state);
        return this;
    }

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

    public void removeState(short id) {
        removeState(getState(id));
    }

    public void removeState(GameState state) {
        if (state instanceof MetaState) {
            throw new IllegalArgumentException("Meta states cannot be removed!");
        }
        Iterator<Map.Entry<Integer, TransitionWrapper>> it = this.transitions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, TransitionWrapper> entry = it.next();
            if (fromComponent(entry.getKey()) == state.id() || toComponent(entry.getKey()) == state.id()) {
                it.remove();
            }
        }
    }

    public GameState getCurrentState() {
        return this.currentState;
    }

    public StateTransition getCurrentTransition() {
        if (this.currentTransition != null) {
            return this.currentTransition.getTransition();
        }
        return null;
    }

    public GameState getStartState() {
        for (Map.Entry<Integer, TransitionWrapper> entry : this.transitions.entrySet()) {
            if (fromComponent(entry.getKey()) == MetaState.ID) {
                return entry.getValue().getTo();
            }
        }
        return null;
    }

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

    public void removeTransition(short from, short to) {
        this.transitions.remove(combine(from, to));
    }

    public void start() {
        GameState start = getStartState();
        if (start == null) {
            throw new IllegalStateException("No start state (state with a transition from NO_STATE) found!");
        }
        transitionTo(start.id());
    }

    public void transitionTo(short stateId) {
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
        switchState(TRANSITION);
        this.currentTransition = transition;
    }

    private void switchState(GameState newState) {
        final GameState current = getCurrentState();
        if (current != null) {
            current.onLeave(context, newState);
        }
        this.currentState = newState;
        System.out.println("New state: " + newState);
        newState.onEnter(context, current);
    }

    public void update(float delta) {
        if (this.currentTransition != null) {
            if (this.currentTransition.transition(this, delta)) {
                switchState(this.currentTransition.getTo());
                this.currentTransition = null;
            }
            return;
        }
        if (!(this.currentState instanceof MetaState)) {
            this.currentState.update(context, delta);
        }
    }

    private static int combine(short a, short b) {
        return (((int) a) << 16) | b;
    }

    private static short fromComponent(int combined) {
        return (short)(combined >> 16);
    }

    private static short toComponent(int combined) {
        return (short)(combined & 0xFFFF);
    }

    private static final class TransitionWrapper implements StateTransition {

        private final StateTransition transition;
        private final GameState from;
        private final GameState to;

        private TransitionWrapper(StateTransition transition, GameState from, GameState to) {
            this.transition = transition;
            this.from = from;
            this.to = to;
        }

        public StateTransition getTransition() {
            return transition;
        }

        public GameState getFrom() {
            return from;
        }

        public GameState getTo() {
            return to;
        }

        @Override
        public boolean transition(StateManager manager, float delta) {
            return this.transition.transition(manager, delta);
        }
    }

    private static abstract class MetaState extends GameState {

        public static final short ID = 0;

        @Override
        public short id() {
            return ID;
        }
    }

    public static final class TransitionState extends MetaState {
        private TransitionState() {
        }
    }

    public static final class StartState extends MetaState {
        private StartState() {
        }
    }

    public static final class EndState extends MetaState {

        private EndState() {
        }

        @Override
        public void onEnter(StateContext context, GameState from) {
            context.getGame().exit();
        }
    }
}
