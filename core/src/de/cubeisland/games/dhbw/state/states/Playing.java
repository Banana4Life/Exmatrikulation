package de.cubeisland.games.dhbw.state.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import de.cubeisland.games.dhbw.entity.component.Deck;
import de.cubeisland.games.dhbw.entity.component.Pickable;
import de.cubeisland.games.dhbw.entity.component.Picked;
import de.cubeisland.games.dhbw.state.GameState;
import de.cubeisland.games.dhbw.state.StateContext;
import de.cubeisland.games.dhbw.state.StateManager;
import de.cubeisland.games.dhbw.util.EntityUtil;

public class Playing extends GameState {

    public static final short ID = 5;

    @Override
    public short id() {
        return ID;
    }

    @Override
    public boolean keyDown(StateContext context, int keycode) {
        if (keycode == Input.Keys.W) {
            context.getEngine().getEntitiesFor(Family.getFor(Deck.class)).first().getComponent(Deck.class).drawCard();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(StateContext context, int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT) {
            return false;
        }
        System.out.println("Click!");
        Entity e = EntityUtil.getEntityAt(context.getEngine(), context.getCamera(), screenX, screenY);
        System.out.println(e);
        if (e != null) {
            e.remove(Pickable.class);
            e.add(new Picked());
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(StateContext context, int screenX, int screenY, int pointer, int button) {
        ImmutableArray<Entity> entities = context.getEngine().getEntitiesFor(Family.getFor(Picked.class));
        if (entities.size() == 0) {
            return false;
        }
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            entity.remove(Picked.class);
            entity.add(new Pickable());
        }
        return true;
    }
}
