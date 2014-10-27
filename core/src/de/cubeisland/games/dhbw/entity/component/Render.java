package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import de.cubeisland.games.dhbw.entity.RenderObject;

public class Render extends Component {
    private RenderObject object = null;

    public Render setObject(RenderObject object) {
        this.object = object;
        return this;
    }

    public RenderObject getObject() {
        return object;
    }
}
