package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * The Transform saves a position, a rotation and a scale and allows operations on them.
 *
 * @author Phillip Schichtel
 * @author Jonas Dann
 */
public class Transform extends Component {
    private final Vector3 position = Vector3.Zero.cpy();
    private final Quaternion rotation = new Quaternion(Vector3.Zero, 0);
    private float scale = 1;

    /**
     * Get the position Vector of the Transform.
     *
     * @return The position Vector.
     */
    public Vector3 getPosition() {
        return this.position.cpy();
    }

    /**
     * Set the position Vector of the Transform.
     *
     * @param pos The new position Vector.
     * @return Return this.
     */
    public Transform setPosition(Vector3 pos) {
        this.position.set(pos);
        return this;
    }

    /**
     * Move the transform the distance given.
     *
     * @param distance The distance to move the transform.
     * @return Return this.
     */
    public Transform move(Vector3 distance) {
        this.position.add(distance);
        return this;
    }

    /**
     * Move the transform on the given axes.
     *
     * @param x The distance to move on the x axis.
     * @param y The distance to move on the y axis.
     * @param z The distance to move on the z axis.
     * @return Return this.
     */
    public Transform move(float x, float y, float z) {
        this.position.add(x, y, z);
        return this;
    }

    /**
     * Get the rotation of the Transformation.
     *
     * @return Returns a Quaternion with the rotation.
     */
    public Quaternion getRotation() {
        return this.rotation.cpy();
    }

    /**
     * Set the rotation of the Transform th the given rotation.
     *
     * @param rot The rotation to use.
     * @return Returns this.
     */
    public Transform setRotation(Quaternion rot) {
        this.rotation.set(rot);
        return this;
    }

    /**
     * Rotate the Transform on three axes.
     *
     * @param yaw   Yaw rotation.
     * @param pitch Pitch rotation.
     * @param roll  Roll rotation.
     * @return Returns this.
     */
    public Transform rotate(float yaw, float pitch, float roll) {
        this.rotation.setEulerAngles(rotation.getYaw() + yaw, rotation.getPitch() + pitch, rotation.getRoll() + roll);
        return this;
    }

    /**
     * Get the scale of the Transform.
     *
     * @return Returns the scale.
     */
    public float getScale() {
        return scale;
    }

    /**
     * Set the scale of the Transform to the given scale.
     *
     * @param scale The scale.
     * @return Returns this.
     */
    public Transform setScale(float scale) {
        this.scale = scale;
        return this;
    }

    /**
     * Tests if two Transforms are equal.
     *
     * @param obj Tranform to compare to.
     * @return Returns true if the Transforms are equal. Returns false if they are not.
     */
    @Override
    public boolean equals(Object obj) {
        return Transform.class.isAssignableFrom(obj.getClass()) && (((Transform) obj).getPosition().equals(this.position) && ((Transform) obj).getRotation().equals(this.rotation) /*No supported now :) && ((Transform) obj).getScale() == this.scale*/);
    }
}
