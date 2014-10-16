package de.cubeisland.games.dhbw.util.renderobject;

import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.entity.component.Transform;

public abstract class RenderObject {
    public abstract void render(Transform transform, DHBWGame game);
}
