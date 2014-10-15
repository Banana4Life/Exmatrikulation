package de.cubeisland.games.dhbw.state;

import de.cubeisland.games.dhbw.DHBWGame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StateManager {

    public static final StartState START = new StartState();

    private final DHBWGame game;
    private GameState currentState;
    private TransitionWrapper currentTransition;
    private final Map<Short, GameState> states;
    private final Map<Integer, TransitionWrapper> transitions;

    public StateManager(DHBWGame game) {
        this.game = game;
        this.states = new HashMap<>();
        this.transitions = new HashMap<>();
        this.currentState = START;
    }

    public DHBWGame getGame() {
        return this.game;
    }

    public StateManager addState(GameState state) {
        final short id = state.id();
        if (id == StartState.ID) {
            throw new IllegalArgumentException("State id " + StartState.ID + " is reserved!");
        }
        GameState existing = this.states.get(id);
        if (existing != null) {
            throw new IllegalArgumentException("There is already a state registered on id " + id + ": " + existing.getClass().getName());
        }
        this.states.put(id, state);
        return this;
    }

    public GameState getState(short id) {
        if (id == StartState.ID) {
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
        if (state == START) {
            throw new IllegalArgumentException("The start state " + StartState.ID + " cannot be removed!");
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
            if (fromComponent(entry.getKey()) == StartState.ID) {
                return entry.getValue().getTo();
            }
        }
        return null;
    }

    public StateManager addTransition(short fromId, short toId, StateTransition transition) {
        if (fromId == StartState.ID) {
            GameState start = getStartState();
            if (start != null) {
                throw new IllegalArgumentException("There is already a start state defined: " + start.getClass().getName() + " (" + start.id() + ")");
            }
        }
        this.transitions.put(combine(fromId, toId), new TransitionWrapper(transition, getState(fromId), getState(toId)));
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
        TransitionWrapper transition = this.transitions.get(combine(getCurrentState().id(), stateId));
        if (transition == null) {
            throw new IllegalArgumentException("There is no transition defined from id " + getCurrentState().id() + " to " + stateId);
        }
        this.currentTransition = transition;
    }

    private void switchState(GameState newState) {
        final GameState current = getCurrentState();
        if (current != null) {
            current.onLeave(this, newState);
        }
        this.currentState = newState;
        newState.onEnter(this, current);
    }

    public void update(float delta) {
        if (this.currentTransition != null) {
            if (this.currentTransition.transition(this, delta)) {
                switchState(this.currentTransition.getTo());
                this.currentTransition = null;
            }
            return;
        }
        if (this.currentState != START) {
            this.currentState.update(this, delta);
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

    public static final class StartState implements GameState {

        public static final short ID = 0;

        protected StartState() {
        }

        @Override
        public short id() {
            return ID;
        }

        @Override
        public void onEnter(StateManager manager, GameState from) {
        }

        @Override
        public void onLeave(StateManager manager, GameState to) {
        }

        @Override
        public void update(StateManager manager, float delta) {
        }
    }
}
