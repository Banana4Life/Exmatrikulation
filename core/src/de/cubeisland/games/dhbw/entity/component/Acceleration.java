package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class Acceleration extends Component {
    private final Vector3 acceleration = Vector3.Zero.cpy();

    public Acceleration set(Vector3 v) {
        this.acceleration.set(v);
        return this;
    }

    public Vector3 get() {
        return this.acceleration.cpy();
    }
}
