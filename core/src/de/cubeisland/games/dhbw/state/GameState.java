package de.cubeisland.games.dhbw.state;

public interface GameState {
    short id();
    void onEnter(StateManager manager, GameState from);
    void onLeave(StateManager manager, GameState to);
    void update(StateManager manager, float delta);
}
