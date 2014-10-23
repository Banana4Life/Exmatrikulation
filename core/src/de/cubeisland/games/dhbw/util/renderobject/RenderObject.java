package de.cubeisland.games.dhbw.util.renderobject;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.cubeisland.games.dhbw.DHBWGame;

public abstract class RenderObject {
    public abstract void render(PerspectiveCamera cam, Entity e, DHBWGame game);
}
