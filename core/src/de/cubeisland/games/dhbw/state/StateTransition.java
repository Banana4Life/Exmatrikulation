package de.cubeisland.games.dhbw.state;

public interface StateTransition {
    boolean transition(StateManager manager, float delta);
}
