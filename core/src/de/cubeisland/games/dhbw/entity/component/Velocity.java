package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class Velocity extends Component {
    private final Vector3 velocity = Vector3.Zero.cpy();

    public Velocity set(Vector3 v) {
        this.velocity.set(v);
        return this;
    }

    public Vector3 get() {
        return this.velocity.cpy();
    }

    public Velocity accelerate(Vector3 a) {
        this.velocity.add(a);
        return this;
    }
}
