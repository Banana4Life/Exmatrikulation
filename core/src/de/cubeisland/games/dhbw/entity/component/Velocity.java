package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * The Velocity is a Component that saves the velocity of an object.
 * @author Phillip Schichtel
 */
public class Velocity extends Component {
    private final Vector3 velocity = Vector3.Zero.cpy();

    /**
     * Set the velocity Vecotr.
     * @param v The new velocity to use.
     * @return Returns this.
     */
    public Velocity set(Vector3 v) {
        this.velocity.set(v);
        return this;
    }

    /**
     * Get the velocity Vector.
     * @return A copy of the velocity Vector.
     */
    public Vector3 get() {
        return this.velocity.cpy();
    }

    /**
     * Applies the given acceleration to the velocity.
     * @param a The acceleration to apply.
     * @return Returns this.
     */
    public Velocity accelerate(Vector3 a) {
        this.velocity.add(a);
        return this;
    }
}
