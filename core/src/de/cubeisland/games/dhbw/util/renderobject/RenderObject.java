package de.cubeisland.games.dhbw.util.renderobject;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Transform;

public interface RenderObject {
    void render(DHBWGame game, Camera cam, Entity e, Transform transform);
    boolean isWithin(Camera cam, float x, float y);
}
