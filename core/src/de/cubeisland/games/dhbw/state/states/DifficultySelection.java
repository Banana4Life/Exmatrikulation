package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.state.GameState;

import java.util.ArrayList;
import java.util.List;

public class DifficultySelection extends MenuState {

    public static final short ID = 5;

    @Override
    public short id() {
        return ID;
    }
}
