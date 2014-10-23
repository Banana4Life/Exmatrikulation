package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class Camera extends Component {

    private PerspectiveCamera camera;

    public PerspectiveCamera get() {
        return camera;
    }

    public Camera set(PerspectiveCamera camera) {
        this.camera = camera;
        return this;
    }
}
