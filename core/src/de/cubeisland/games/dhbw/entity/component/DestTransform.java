package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class DestTransform extends Transform {
    public DestTransform(Transform tranform) {
        setPosition(tranform.getPosition().cpy());
        setRotation(tranform.getRotation().cpy());
        setScale(tranform.getScale());
    }

    public DestTransform(Vector3 position, Quaternion rotation) {
        setPosition(position.cpy());
        setRotation(rotation.cpy());
    }
}
