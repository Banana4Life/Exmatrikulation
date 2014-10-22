package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class DestTransform extends Transform {
    public DestTransform(Transform tranform) {
        this.setPosition(tranform.getPosition().cpy());
        this.setRotation(tranform.getRotation().cpy());
    }

    public DestTransform(Vector3 position, Quaternion rotation) {
        this.setPosition(position.cpy());
        this.setRotation(rotation.cpy());
    }
}
