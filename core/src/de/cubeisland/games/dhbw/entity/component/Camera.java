package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.PerspectiveCamera;

/**
 * Camera encapsulates the PerspectiveCamera as Component.
 *
 * @author Phillip Schichtel
 */
public class Camera extends Component {

    private PerspectiveCamera camera;

    /**
     * Returns the PerspectiveCamera.
     *
     * @return PerspectiveCamera
     */
    public PerspectiveCamera get() {
        return camera;
    }

    /**
     * Sets the PerspectiveCamera.
     *
     * @param camera The new Camera to use.
     * @return Returns this.
     */
    public Camera set(PerspectiveCamera camera) {
        this.camera = camera;
        return this;
    }
}
