package de.cubeisland.games.dhbw.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.cubeisland.games.dhbw.entity.component.Pickable;
import de.cubeisland.games.dhbw.entity.component.Picked;
import de.cubeisland.games.dhbw.entity.component.Render;
import de.cubeisland.games.dhbw.state.StateManager;

public class GlobalInputProcessor implements InputProcessor {
    private final PerspectiveCamera camera;
    private final Engine engine;
    private final StateManager sm;

    public GlobalInputProcessor(PerspectiveCamera camera, Engine engine, StateManager stateManager) {
        this.camera = camera;
        this.engine = engine;
        this.sm = stateManager;
    }

//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        if (button == Input.Buttons.LEFT) {
//            ImmutableArray<Entity> entities = this.engine.getEntitiesFor(Family.getFor(Render.class));
//
//            for (int i = 0; i < entities.size(); ++i) {
//                Entity e = entities.get(i);
//                float x = screenX;
//                float y = Gdx.graphics.getHeight() - screenY;
//                if (e.getComponent(Render.class).getObject().isWithin(camera, x, y)) {
//                    state().entityTouched(e, x, y);
//                }
//            }
//            ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.getFor(Pickable.class, Render.class));
//            for (int i = 0; i < entities.size(); ++i) {
//                Entity entity = entities.get(i);
//                if (entity.getComponent(Render.class).getObject().isWithin(camera, screenX, Gdx.graphics.getHeight() - screenY)) {
//                    entity.remove(Pickable.class);
//                    entity.add(new Picked());
//                }
//            }
//        }
//        return true;
//    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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
