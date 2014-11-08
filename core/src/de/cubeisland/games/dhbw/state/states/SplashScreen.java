package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.entity.component.Transform;
import de.cubeisland.games.dhbw.entity.object.ImageObject;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;

public class SplashScreen extends GameState {

    public static final short ID = 1;

    private Entity splash;

    @Override
    public short id() {
        return ID;
    }

    public Entity getSplash() {
        return splash;
    }

    @Override
    public void onEnter(StateContext context, GameState from) {
        DHBWGame game = context.getGame();
        splash = game.getEntityFactory().create(game.getResources().entities.image);
        splash.getComponent(Render.class).setObject(new ImageObject(new Texture("images/splashscreen.png")));
        splash.getComponent(Transform.class).setPosition(new Vector3(0, 0, -280)).setScale(.344f);

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
