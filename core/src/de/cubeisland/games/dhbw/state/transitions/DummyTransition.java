package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

public class DummyTransition extends StateTransition {

    public static final DummyTransition INSTANCE = new DummyTransition();

    private int i;
    private static final int MAX = 5;

    @Override
    public void begin(StateContext context, GameState origin, GameState destination) {
        this.i = 1;
    }

    @Override
    public boolean transition(StateContext context, GameState origin, GameState destination, float delta) {
        if (this.i <= MAX) {
            System.out.println("Transition! " + this.i++ + "/" + MAX);
            return false;
        }
        return true;
    }
}
