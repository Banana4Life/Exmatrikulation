package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * DestTransform extends Transform. It just has another name and constructors.
 *
 * @author Jonas Dann
 */
public class DestTransform extends Transform {
    /**
     * Constructor to set the position, rotation and scale to the given transform.
     *
     * @param tranform The transform data.
     */
    public DestTransform(Transform tranform) {
        this(tranform.getPosition(), tranform.getRotation(), tranform.getScale());
    }

    /**
     * Sets the fields of the DestTransform
     * @param position position
     * @param rotation rotation
     * @param scale scale
     */
    public DestTransform(Vector3 position, Quaternion rotation, float scale) {
        setPosition(position.cpy());
        setRotation(rotation.cpy());
        setScale(scale);
    }

    /**
     * Sets the fields of the DestTransform
     * @param position The position to use.
     * @param transform The transform to get the rotation and scale from.
     */
    public DestTransform(Vector3 position, Transform transform) {
        this(position, transform.getRotation(), transform.getScale());
    }

    /**
     * Constructor to set the position and rotation.
     *
     * @param position The position to use.
     * @param rotation The rotation to use.
     */
    public DestTransform(Vector3 position, Quaternion rotation) {
        setPosition(position.cpy());
        setRotation(rotation.cpy());
    }
}
