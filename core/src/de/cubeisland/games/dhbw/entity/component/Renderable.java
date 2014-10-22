package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import de.cubeisland.games.dhbw.DHBWGame;
import de.cubeisland.games.dhbw.util.renderobject.RenderObject;

public class Renderable extends Component {
    private RenderObject renderObject;

    public Renderable(RenderObject renderObject) {
        this.renderObject = renderObject;
    }

    public void render(Entity e, DHBWGame game) {
        renderObject.render(e, game);
    }

    public RenderObject getRenderObject() {
        return renderObject;
    }
}
