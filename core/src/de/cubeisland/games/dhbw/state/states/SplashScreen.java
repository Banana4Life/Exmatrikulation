package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;

/**
 * This
 */
public class SplashScreen extends GameState {

    public static final short ID = 1;

    private Entity splash;

    @Override
    public short id() {
        return ID;
    }

    @Override
    public void onEnter(StateContext context, GameState from) {
        DHBWGame game = context.getGame();
        splash = game.getEntityFactory().createImage("images/splashscreen.png", new Vector3(0, 40, -280), .344f);
        context.getEngine().addEntity(splash);
    }

    @Override
    public void onLeave(StateContext context, GameState to) {
        context.getEngine().removeEntity(splash);
    }

    @Override
    public boolean keyDown(StateContext context, int keycode) {
        return skip(context);
    }

    @Override
    public boolean keyUp(StateContext context, int keycode) {
        return skip(context);
    }

    @Override
    public boolean keyTyped(StateContext context, char character) {
        return skip(context);
    }

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        return skip(context);
    }

    @Override
    public boolean touchUp(StateContext context, int screenX, int screenY, int pointer, int button) {
        return skip(context);
    }

    private static boolean skip(StateContext context) {
        context.transitionTo(MainMenu.ID);
        return true;
    }
}
