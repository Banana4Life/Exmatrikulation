package de.cubeisland.games.dhbw.util.renderobject;

import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.DHBWGame;

public abstract class RenderObject {
    public abstract void render(Entity e, DHBWGame game);
}
