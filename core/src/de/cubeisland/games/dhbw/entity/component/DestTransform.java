package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class DestTransform extends Transform {
    public DestTransform(Transform tranform) {
        this.setPosition(tranform.getPosition());
        this.setRotation(tranform.getRotation());
    }

    public DestTransform(Vector3 position, Quaternion rotation) {
        this.setPosition(position);
        this.setRotation(rotation);
    }
}
