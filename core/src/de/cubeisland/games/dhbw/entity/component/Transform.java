package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Transform extends Component {
    private final Vector3 position = Vector3.Zero.cpy();
    private final Quaternion rotation = new Quaternion(Vector3.Zero, 0);

    public Transform setPosition(Vector3 pos) {
        this.position.set(pos);
        return this;
    }

    public Vector3 getPosition() {
        return this.position.cpy();
    }

    public Transform move(Vector3 distance) {
        this.position.add(distance);
        return this;
    }

    public Transform setRotation(Quaternion rot) {
        this.rotation.set(rot);
        return this;
    }

    public Quaternion getRotation() {
        return this.rotation.cpy();
    }

    public Transform rotate(float yaw, float pitch, float roll) {
        // TODO implement me
        return this;
    }
}
