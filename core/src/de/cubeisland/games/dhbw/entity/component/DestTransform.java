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
        setPosition(tranform.getPosition().cpy());
        setRotation(tranform.getRotation().cpy());
        setScale(tranform.getScale());
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
