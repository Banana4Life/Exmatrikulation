package de.cubeisland.games.dhbw.state.transitions;

import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateTransition;

public class SplashScreenToMainMenuTransition extends StateTransition {

    public static final SplashScreenToMainMenuTransition INSTANCE = new SplashScreenToMainMenuTransition();

    private int i;
    private static final int MAX = 5;

    @Override
    public void begin(StateContext context) {
        this.i = 1;
    }

    @Override
    public boolean transition(StateContext context, float delta) {
//TODO change transiton in dhbw game
        return true;
    }
}
