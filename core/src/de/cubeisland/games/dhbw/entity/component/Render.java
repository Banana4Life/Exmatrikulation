package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import de.cubeisland.games.dhbw.entity.RenderObject;

/**
 * The Render Component holds the RenderObject.
 *
 * @author Jonas Dann
 */
public class Render extends Component {
    private RenderObject object = null;

    /**
     * Set the RenderObject.
     *
     * @param object The RenderObject ot use.
     * @return Returns this.
     */
    public Render setObject(RenderObject object) {
        this.object = object;
        return this;
    }

    /**
     * Get the RenderObject.
     *
     * @return Returns the RenerObject.
     */
    public RenderObject getObject() {
        return object;
    }
}
