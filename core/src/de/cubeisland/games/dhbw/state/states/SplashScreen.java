package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.entity.object.ImageObject;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;

public class SplashScreen extends GameState {

    public static final short ID = 1;

    private Entity background;

    @Override
    public short id() {
        return ID;
    }

    @Override
    public void onEnter(StateContext context, GameState from) {
        DHBWGame game = context.getGame();
        background = game.getEntityFactory().create(game.getResources().entities.image);

        background.getComponent(Render.class).setObject(new ImageObject(new Texture("images/background.png")));

        context.getEngine().addEntity(background);
    }

    @Override
    public void onLeave(StateContext context, GameState to) {
        //context.getEngine().removeEntity(image);
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
