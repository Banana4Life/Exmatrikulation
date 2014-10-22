package de.cubeisland.games.dhbw.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.*;

public class GlobalInputProcessor implements InputProcessor {
    private DHBWGame game;

    public GlobalInputProcessor(DHBWGame game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W) {
            game.getEngine().getEntitiesFor(Family.getFor(Deck.class)).first().getComponent(Deck.class).drawCard();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            for (Entity entity : game.getEngine().getEntitiesFor(Family.getFor(Pickable.class, Model.class))) {
                if (entity.getComponent(Model.class).getModelObject().isClickOnModel(game.getCamera(), screenX, Gdx.graphics.getHeight() - screenY)) {
                    entity.remove(Pickable.class);
                    entity.add(new Picked());
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            for (Entity entity : game.getEngine().getEntitiesFor(Family.getFor(Picked.class)).toArray()) {
                entity.remove(Picked.class);
                entity.add(new Pickable());
            }
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
