package de.cubeisland.games.dhbw.state;

import de.cubeisland.games.dhbw.DHBWGame;

import java.util.HashMap;
import java.util.Map;

public class StateManager {

    public static final short NO_STATE = 0;

    private final DHBWGame game;
    private GameState currentState;
    private short currentStateId;
    private TransitionWrapper currentTransition;
    private final Map<Short, GameState> states;
    private final Map<Integer, TransitionWrapper> transitions;

    public StateManager(DHBWGame game) {
        this.game = game;
        this.states = new HashMap<>();
        this.transitions = new HashMap<>();
    }

    public DHBWGame getGame() {
        return this.game;
    }

    public StateManager addState(GameState state) {
        final short id = state.id();
        if (id == NO_STATE) {
            throw new IllegalArgumentException("State id " + NO_STATE + " is reserved!");
        }
        GameState existing = this.states.get(id);
        if (existing != null) {
            throw new IllegalArgumentException("There is already a state registered on id " + id + ": " + existing.getClass().getName());
        }
        this.states.put(id, state);
        return this;
    }

    public GameState getState(short id) {
        GameState state = this.states.get(id);
        if (state == null) {
            throw new IllegalArgumentException("No state found for id: " + id);
        }
        return state;
    }

    public GameState getCurrentState() {
        if (this.currentStateId == NO_STATE) {
            return null;
        }
        return getState(this.currentStateId);
    }

    public StateTransition getCurrentTransition() {
        if (this.currentTransition != null) {
            return this.currentTransition.getTransition();
        }
        return null;
    }

    public StateManager addTransition(short fromId, short toId, StateTransition transition) {
        this.transitions.put(combine(fromId, toId), new TransitionWrapper(transition, getState(fromId), getState(toId)));
        return this;
    }

    public void start() {
        for (Map.Entry<Integer, TransitionWrapper> entry : this.transitions.entrySet()) {
            if (fromComponent(entry.getKey()) == NO_STATE) {
                this.transitionTo(entry.getValue().getTo().id());
            }
        }
    }

    public void transitionTo(short stateId) {
        TransitionWrapper transition = this.transitions.get(combine(currentStateId, stateId));
        if (transition == null) {
            throw new IllegalArgumentException("There is no transition defined from id " + this.currentStateId + " to " + stateId);
        }
        this.currentTransition = transition;
    }

    private void switchState(GameState newState) {
        final GameState current = getCurrentState();
        if (current != null) {
            current.onLeave(this, newState);
        }
        this.currentStateId = newState.id();
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
        if (this.currentStateId != NO_STATE) {
            if (this.currentState == null || this.currentState.id() != this.currentStateId) {
                this.currentState = getState(this.currentStateId);
            }
            this.currentState.update(this, delta);
        }
    }

    private static int combine(short a, short b) {
        return ((int) a << 16) & b;
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
}
