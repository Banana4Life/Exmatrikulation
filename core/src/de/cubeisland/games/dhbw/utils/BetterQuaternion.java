package de.cubeisland.games.dhbw.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class BetterQuaternion extends Quaternion {

    public BetterQuaternion(Vector3 axis, float angle) {
        super(axis, angle);
    }

    public BetterQuaternion(BetterQuaternion quaternion) {
        this.set(quaternion);
    }

    @Override
    public float getAngleAroundRad (final float axisX, final float axisY, final float axisZ) {
        final float d = Vector3.dot(this.x, this.y, this.z, axisX, axisY, axisZ);
        final float l2 = Quaternion.len2(axisX * d, axisY * d, axisZ * d, this.w);
        return MathUtils.isZero(l2) ? 0f : (float)(2.0 * Math.acos((float) (this.w / Math.sqrt(l2))));
    }

    @Override
    public BetterQuaternion cpy() {
        return new BetterQuaternion(this);
    }
}
