package de.cubeisland.games.dhbw.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.Pickable;
import de.cubeisland.games.dhbw.entity.component.Picked;
import de.cubeisland.games.dhbw.entity.component.Render;

public class GlobalInputProcessor implements InputProcessor {
    private final PerspectiveCamera camera;
    private final Engine engine;

    public GlobalInputProcessor(PerspectiveCamera camera, Engine engine) {
        this.camera = camera;
        this.engine = engine;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W) {
            engine.getEntitiesFor(Family.getFor(Deck.class)).first().getComponent(Deck.class).drawCard();
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
            ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.getFor(Pickable.class, Render.class));
            for (int i = 0; i < entities.size(); ++i) {
                Entity entity = entities.get(i);
                if (entity.getComponent(Render.class).getObject().isWithin(camera, screenX, Gdx.graphics.getHeight() - screenY)) {
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
            ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.getFor(Picked.class));
            for (int i = 0; i < entities.size(); ++i) {
                Entity entity = entities.get(i);
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
