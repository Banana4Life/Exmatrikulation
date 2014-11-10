package de.cubeisland.games.dhbw.state.states;

import de.cubeisland.games.dhbw.state.GameState;

/**
 * In this state the game is paused
 *
 * @author Tim Aadamek
 */
public class Paused extends GameState {

    public static final short ID = 9;

    @Override
    public short id() {
        return ID;
    }
}
