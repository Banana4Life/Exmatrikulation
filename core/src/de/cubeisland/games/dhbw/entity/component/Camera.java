package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;

/**
 * Camera encapsulates the PerspectiveCamera as Component.
 *
 * @author Phillip Schichtel
 */
public class Camera extends Component {

    private OrthographicCamera orthographic;
    private PerspectiveCamera perspective;

    /**
     * Returns the orthographic camera for 2D rendering.
     *
     * @return the camera for 2D rendering
     */
    public OrthographicCamera getOrthographic() {
        return orthographic;
    }

    /**
     * Sets a perspective camera for 2D rendering.
     *
     * @param orthographic the camera to set
     * @return fluent interface
     */
    public Camera setOrthographic(OrthographicCamera orthographic) {
        this.orthographic = orthographic;
        return this;
    }

    /**
     * Returns the perspective camera for 3D rendering.
     *
     * @return the camera for 3D rendering
     */
    public PerspectiveCamera getPerspective() {
        return perspective;
    }

    /**
     * Sets a perspective camera for 3D rendering.
     *
     * @param camera the new camera to set
     * @return fluent interface
     */
    public Camera setPerspective(PerspectiveCamera camera) {
        this.perspective = camera;
        return this;
    }
}
