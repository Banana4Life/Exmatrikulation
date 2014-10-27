package de.cubeisland.games.dhbw.state;

public abstract class StateTransition {
    public void begin(StateContext context) {

    }

    public abstract boolean transition(StateContext context, float delta);

    public void finish(StateContext context) {

    }

}
